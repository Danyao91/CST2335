package com.example.androidlabs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {
    private ProgressBar progressBar;
    private ImageView weatherView;
    private TextView speedText;
    private TextView minText;
    private TextView maxText;
    private TextView valueText;
    private TextView UVText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        weatherView = findViewById(R.id.curerntW);
        speedText = findViewById(R.id.speedW);
        valueText= findViewById(R.id.currentT);
        minText = findViewById(R.id.minT);
        maxText = findViewById(R.id.maxT);
        UVText = findViewById(R.id.uvR);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        new ForecastQuery().execute();


    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {
        private String iconName = "";
        private URL url = null;
        private Bitmap bm ;
        private String speed;
        private String value;
        private String min;
        private String max;
        private float uv;

        @Override
        protected String doInBackground(String... strings) {

            try {
                url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric");

                //create the network connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream inStream = urlConnection.getInputStream();

                //create a pull parser
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = Xml.newPullParser();
                xpp.setInput(inStream, "UTF-8");

                int eventType = xpp.getEventType();

                //loop over XML
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        String tagName = xpp.getName();
                        if (tagName.equals("speed")) {
                            speed = xpp.getAttributeValue(null, "value");
                            Log.e("AsyncTask", "Found parameter wind speed: " + speed);
                            publishProgress(0);
                        } else if (tagName.equals("temperature")) {
                            min = xpp.getAttributeValue(null, "min");
                            Log.e("AsyncTask", "Found parameter min temperature: " + min);
                            publishProgress(25);

                            max = xpp.getAttributeValue(null, "max");
                            Log.e("AsyncTask", "Found parameter max temperature: " + max);
                            publishProgress(50);

                            value = xpp.getAttributeValue(null, "value");
                            Log.e("AsyncTask", "Found parameter max temperature: " + value);

                            publishProgress(75);


                        }
                    }
                    eventType = xpp.next();
                }

            } catch (Exception e) {
                e.printStackTrace();

            }

            if (!fileExistance(iconName + ".png")) {
                FileOutputStream outputStream = null;
                Bitmap image = null;



                try {
                    url = new URL("http://openweathermap.org/img/w/");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    urlConnection.connect();
                    int responseCode = urlConnection.getResponseCode();
                    if(responseCode ==200) {
                        image = BitmapFactory.decodeStream(urlConnection.getInputStream());
                    }

                    image = HttpUtils.getImage("http://openweathermap.org/img/w/");
                    outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);

                    image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    publishProgress(100);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i("WeatherForecast", "Image Downloading");

            } else {
                Log.i("WeatherForecast", "Found the image locally");
            }

            FileInputStream fis = null;
            try {
                fis = openFileInput(iconName + ".png");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            bm = BitmapFactory.decodeStream(fis);


            try {
                url = new URL("http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389");

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inStream = urlConnection.getInputStream();


                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }

                String result = sb.toString();
                JSONObject jObject = new JSONObject(result);
                uv = (float) jObject.getDouble("value");
                Log.e("AsyncTask", "Found parameter UV rate: " + uv);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar = (ProgressBar)findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);

            //super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            progressBar = (ProgressBar)findViewById(R.id.progressBar);
            progressBar.setVisibility(View.INVISIBLE);

            weatherView.setImageBitmap(bm);
            speedText.setText("The wind speed in Ottawa is " + speed + " mph");
            valueText.setText("The current temperature in Ottawa is " + value + " °c");
            minText.setText("The minimum temperature in Ottawa is " + min + " °c");
            maxText.setText("The maximum temperature in Ottawa is " + max + " °c");
            maxText.setText("The maximum temperature in Ottawa is " + max + " °c");
            UVText.setText("The current UV rate in Ottawa is " + uv + " °c");


        }
    }

        private boolean fileExistance(String fname) {
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        private static class HttpUtils {
            public static Bitmap getImage(URL url) {
                HttpURLConnection connection = null;
                try {
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        return BitmapFactory.decodeStream(connection.getInputStream());

                    } else
                        return null;
                } catch (Exception e) {
                    return null;
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }

            public static Bitmap getImage(String urlString) {
                try {
                    URL url = new URL(urlString);
                    return getImage(url);
                } catch (MalformedURLException e) {
                    return null;
                }
            }
        }






}
