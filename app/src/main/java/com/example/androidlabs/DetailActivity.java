package com.example.androidlabs;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


public class DetailActivity extends FragmentActivity {

/*    private boolean isTablet;
    private Bundle dataFromActivity;
    private long id;
    private String sORr;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        Bundle bundle = new Bundle();
        bundle.putString("message", getIntent().getStringExtra("message"));
        bundle.putLong("messageId", getIntent().getLongExtra("messageId", 0));
        bundle.putString("sORr", getIntent().getStringExtra("sORr"));

        FragmentTablet fragmentTablet = new FragmentTablet();
        fragmentTablet.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.messagedetailsFrameLayout, fragmentTablet).commit();
    }

}
