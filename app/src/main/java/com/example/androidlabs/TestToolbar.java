package com.example.androidlabs;

import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity {

    private String currentMessage = "This is the initial message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);

        Toolbar tBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(tBar);


    }


    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            //what to do when the menu item is selected:
            case R.id.item1:
                Toast.makeText(this, currentMessage, Toast.LENGTH_SHORT).show();
                break;

            case R.id.item2:
                LayoutInflater inflater = this.getLayoutInflater();
                View new_message = inflater.inflate(R.layout.dialogbox, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setView(new_message).setPositiveButton("enter",(dialog1, which) -> {
                    EditText newMessageEditText = new_message.findViewById(R.id.EditText);
                    currentMessage = newMessageEditText.getText().toString();
                    onStop();
                }).setNegativeButton("cancel", (dialog1, which) -> onStop()).show();

                    //builder.create().show();
                    break;

            case R.id.item3:

                Snackbar sb = Snackbar.make(findViewById(R.id.item3), "Go Back?", Snackbar.LENGTH_LONG)
                .setAction("Yes!", e -> finish());
                sb.show();

                break;

            case R.id.item4:
                //Show the toast immediately:
                Toast.makeText(this, "You clicked on the overflow menu", Toast.LENGTH_SHORT).show();

                }
        return true;
    }


}
