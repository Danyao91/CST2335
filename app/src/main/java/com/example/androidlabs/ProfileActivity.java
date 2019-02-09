package com.example.androidlabs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageButton mImageButton;
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profileactivity);
        Log.e(ACTIVITY_NAME, "In onCreate()");

        Button nextButton = findViewById(R.id.button2);
        nextButton.setOnClickListener ( b -> {

            Intent nextPage = new Intent (ProfileActivity.this, ChatRoomActivity.class);
            startActivityForResult(nextPage, 2);

        });


        Intent previousPage = getIntent();

        String previousTyped = previousPage.getStringExtra("email");

        EditText enterText = (EditText)getWindow().findViewById(R.id.edit2);
        enterText.setText(previousTyped);


        mImageButton = (ImageButton) getWindow().findViewById(R.id.button1);
        mImageButton.setOnClickListener(b -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton.setImageBitmap(imageBitmap);
        }
    }


}
