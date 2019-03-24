package com.example.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FragmentTablet extends Fragment {

    private TextView messageTextView;
    private TextView messageIdTextView;
    private TextView messagesORrTextView;
    private Button deleteThisMessageButton;

    public static FragmentTablet newInstance(boolean isTablet, String message, long messageId, String sORr) {
        Bundle args = new Bundle();
        args.putBoolean("isTablet", isTablet);
        args.putString("message", message);
        args.putLong("messageId", messageId);
        args.putString("sORr", sORr);
        FragmentTablet fragmentTablet = new FragmentTablet();
        fragmentTablet.setArguments(args);
        return fragmentTablet;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_detail, container, false);

        messageTextView = view.findViewById(R.id.messageDetail);
        messageIdTextView = view.findViewById(R.id.IDDetail);
        messagesORrTextView = view.findViewById(R.id.sORr);
        deleteThisMessageButton = view.findViewById(R.id.ButtonDelete);

        messageTextView.setText(getArguments().getString("message"));
        messageIdTextView.setText(String.valueOf(getArguments().getLong("messageId")));
        messagesORrTextView.setText(getArguments().getString("sORr"));
        boolean isTablet = getArguments().getBoolean("isTablet");

        deleteThisMessageButton.setOnClickListener(v -> {
            if (isTablet) {
                ((ChatRoomActivity) getActivity()).deleteMessage((int) getArguments().getLong("messageId"));
                getFragmentManager().beginTransaction().remove(FragmentTablet.this).commit();
            } else {
                Intent intent = new Intent();
                intent.putExtra("messageId", getArguments().getLong("messageId"));
                getActivity().setResult(Activity.RESULT_OK, intent);
                getActivity().finish();
            }
        });

        return view;
    }
}
