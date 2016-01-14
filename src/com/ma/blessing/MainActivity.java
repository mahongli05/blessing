package com.ma.blessing;

import org.kymjs.chat.ChatActivity;

import com.ma.blessing.ui.ContactPage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity implements OnClickListener {

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_blessing);
        setContentView(new ContactPage(this));
        setupView();
    }

    private void setupView() {
//        mButton = (Button) findViewById(R.id.sent_sms);
//        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

//        SmsHelper.getInstance().sendMessage(this, "13986191267", "This is a test message, ^^");
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }
}
