package com.example.androidlabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {

    private List<Message> messages;
    private ListView listView;
    private Button ButtonSend;
    private Button ButtonReceive;
    private EditText messageEditText;
    private SQLiteDatabase db;
    private MyDatabaseOpenHelper dbOpener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatroomactivity);

        messages = new ArrayList<>();

        listView = findViewById(R.id.List);
        ButtonSend = findViewById(R.id.ButtonSend);
        ButtonReceive = findViewById(R.id.ButtonReceive);
        messageEditText = findViewById(R.id.EditText);

        //get a database
         dbOpener= new MyDatabaseOpenHelper(this);
        db = dbOpener.getWritableDatabase();

        //query all the results from database;
        String[] columns = {MyDatabaseOpenHelper.COL_ID, MyDatabaseOpenHelper.COL_MESSAGE, MyDatabaseOpenHelper.COL_SEND};
        Cursor results = db.query(false, MyDatabaseOpenHelper.TABLE_NAME, columns, null, null, null, null, null, null);

        //find tge column indices
        int idColIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_ID);
        int messageColIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_MESSAGE);
        int isSentColIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_SEND);

        //iterate over the results, return true if there is a next item:
        while(results.moveToNext()){
            String message = results.getString(messageColIndex);
            String send = results.getString(isSentColIndex);
            long id = results.getLong(idColIndex);


            if(send.equals("TRUE"))
                //add the new message to the array list
                messages.add(new Message(id, message,true));
            if(send.equals("FALSE"))
                messages.add(new Message(id, message,false));

        }

        //create an adapter object and send it to the listView
        final ChatAdapter messageAdapter = new ChatAdapter(this, 0);
        listView.setAdapter(messageAdapter);

        ButtonSend.setOnClickListener(v -> {
            String message = messageEditText.getText().toString();
            String send = ButtonSend.getText().toString();


            // add to the database and get the new ID
            ContentValues newRowValues = new ContentValues();
            //put string message in the message column
            newRowValues.put(MyDatabaseOpenHelper.COL_MESSAGE, message);
            newRowValues.put(MyDatabaseOpenHelper.COL_SEND,"TRUE");

            //insert to the database
            long newId = db.insert(MyDatabaseOpenHelper.TABLE_NAME,null,newRowValues);

            boolean sent = false;
            //create new Message object
            if(send.equals("SEND"))
                sent = true;

            Message newMessage = new Message(newId, message,sent);

            //add the new message to the list
            messages.add(newMessage);
            //update the listView
            messageAdapter.notifyDataSetChanged();

            //clear the EditText fields
            messageEditText.setText("");

        });

        ButtonReceive.setOnClickListener(v -> {
            String message = messageEditText.getText().toString();
            String send = ButtonReceive.getText().toString();

            // add to the database and get the new ID
            ContentValues newRowValues = new ContentValues();
            //put string message in the message column
            newRowValues.put(MyDatabaseOpenHelper.COL_MESSAGE, message);
            newRowValues.put(MyDatabaseOpenHelper.COL_SEND,"FALSE");

            //insert to the database
            long newId = db.insert(MyDatabaseOpenHelper.TABLE_NAME,null,newRowValues);

            //create new Message object
            Message newMessage = new Message(newId, message, Boolean.valueOf(send));

            //add the new message to the list
            messages.add(newMessage);
            //update the listView
            messageAdapter.notifyDataSetChanged();

            //clear the EditText fields
            messageEditText.setText("");
        });

        printCursor(results);
    }


    protected class ChatAdapter extends ArrayAdapter<Message> {

        public ChatAdapter(@NonNull Context context, int resource) {
            super(context, resource);
        }

        @Override
        public int getCount() {
            return messages.size();
        }

        public Message getItem(int position) {
            return messages.get(position);
        }


        public View getView(int position, View old, ViewGroup parent) {
            LayoutInflater inflater = ChatRoomActivity.this.getLayoutInflater();

            View newView = null;
            Message message = getItem(position);

            if (message.getIsSent()) {
                newView = inflater.inflate(R.layout.message_send, null);
            }
            if (!(message.getIsSent())) {
                newView = inflater.inflate(R.layout.message_receive, null);
            }


            TextView messageTextView = newView.findViewById(R.id.message_text);
            messageTextView.setText(message.getMessage());

            return newView;
        }
    }


        public void printCursor(Cursor c) {
            Log.d("SQL DATABASE", "VERSION NUMBER: " + db.getVersion());
            Log.d("Cursor", "number of columns: " + c.getColumnCount());
            for(int i=0; i<c.getColumnCount();i++){
                Log.d("Cursor", "name of the columns: " + c.getColumnNames()[i]);
            }
            Log.d("Cursor", "number of results: " + c.getCount());
            Log.d("Cursor", "Each row of results in the cursor: ");
            c.moveToFirst();
            while (!c.isAfterLast()) {
                //int count = 1;
                Log.d("Cursor", " id: " + c.getLong(c.getColumnIndex(dbOpener.COL_ID)) + " message: " + c.getString(c.getColumnIndex(dbOpener.COL_MESSAGE))
                        + " isSENT: " + c.getString(c.getColumnIndex(dbOpener.COL_SEND)));
                c.moveToNext();

            }

        }

        public long getItemId(int position) {
            return position;
        }

}
