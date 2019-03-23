package com.example.androidlabs;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MessageDetails extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        Bundle bundle = new Bundle();
        bundle.putString("message", getIntent().getStringExtra("message"));
        bundle.putLong("messageId", getIntent().getLongExtra("messageId", 0));
        bundle.putBoolean("isASendTextView", getIntent().getBooleanExtra("isASendTextView", true));

        MessageFragment messageFragment = new MessageFragment();
        messageFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.messagedetailsFrameLayout, messageFragment).commit();
    }
}
