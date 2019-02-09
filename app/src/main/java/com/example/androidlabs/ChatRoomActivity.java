package com.example.androidlabs;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {

    private List<String> messages;
    private ListView listView;
    private Button ButtonSend;
    private Button ButtonReceive;
    private EditText EditText;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatroomactivity);

        messages = new ArrayList<>();

        listView = findViewById(R.id.List);
        ButtonSend = findViewById(R.id.ButtonSend);
        ButtonReceive = findViewById(R.id.ButtonReceive);
        EditText = findViewById(R.id.EditText);

        final ChatAdapter messageAdapter = new ChatAdapter(this, 0);
        listView.setAdapter(messageAdapter);

        ButtonSend.setOnClickListener(v -> {
            messages.add(EditText.getText().toString());
            EditText.setText("");

        });
    }


    protected class ChatAdapter extends ArrayAdapter<String>
    {

        public ChatAdapter(@androidx.annotation.NonNull Context context, int resource) {
            super(context, resource);
        }

        @Override
        public int getCount() {
            return messages.size();
        }

        public String getItem(int position){
            return messages.get(position);
        }



        public View getView(int position, View old, ViewGroup parent)
        {
            LayoutInflater inflater = ChatRoomActivity.this.getLayoutInflater();

            View newView = null;


            if (ButtonSend.isClickable() == true)
            newView = inflater.inflate(R.layout.message_send, null);
            else  newView = inflater.inflate(R.layout.message_receive, null);

            TextView message = newView.findViewById(R.id.EditText);
            message.setText(getItem(position));

            return newView;
        }

        public long getItemId(int position)
        {

            return position;
        }
    }

}
