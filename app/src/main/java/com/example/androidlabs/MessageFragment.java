package com.example.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MessageFragment extends Fragment {

    private TextView messageTextTextView;
    private TextView databaseIdTextView;
    private TextView isASendTextView;
    private Button deleteMessageButton;

    public static MessageFragment newInstance(boolean isTablet, String message, long messageId, boolean isASendTextView) {
        Bundle args = new Bundle();
        args.putBoolean("isTablet", isTablet);
        args.putString("message", message);
        args.putLong("messageId", messageId);
        args.putBoolean("isASendTextView", isASendTextView);
        MessageFragment messageFragment = new MessageFragment();
        messageFragment.setArguments(args);
        return messageFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tablet, container, false);

        messageTextTextView = view.findViewById(R.id.messageTextTextView);
        databaseIdTextView = view.findViewById(R.id.databaseIdTextView);
        isASendTextView = view.findViewById(R.id.isASendTextView);
        deleteMessageButton = view.findViewById(R.id.deleteMessageButton);

        messageTextTextView.setText(getArguments().getString("message"));
        databaseIdTextView.setText(String.valueOf(getArguments().getLong("messageId")));
        isASendTextView.setText(((String.valueOf(getArguments().getBoolean("isASendTextView")))));
        boolean isTablet = getArguments().getBoolean("isTablet");


        deleteMessageButton.setOnClickListener(v -> {
            if (isTablet) {
                ((ChatRoomActivity) getActivity()).deleteMessage((int) getArguments().getLong("messageId"));
                getFragmentManager().beginTransaction().remove(MessageFragment.this).commit();
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
