package com.example.androidlabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText emailEditText;
    private SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        Button nextButton = findViewById(R.id.button);
        nextButton.setOnClickListener( b -> {

            Intent nextPage = new Intent(MainActivity.this, ProfileActivity.class);
            nextPage.putExtra("email",emailEditText.getText().toString());
            startActivityForResult(nextPage,2);

        });


        emailEditText = getWindow().findViewById(R.id.emailEditText);
        sp = getSharedPreferences("Email", Context.MODE_PRIVATE);
        String savedString = sp.getString("ReserveName", "");

        emailEditText.setText(savedString);

    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = sp.edit();

        String whatWasTyped = emailEditText.getText().toString();
        editor.putString("ReserveName", whatWasTyped);

        editor.commit();
    }
}
