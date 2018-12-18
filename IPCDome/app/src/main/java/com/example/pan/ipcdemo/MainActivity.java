package com.example.pan.ipcdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        Button bt_bundle = (Button) findViewById(R.id.bt_bundle);
        Button bt_file = (Button) findViewById(R.id.bt_file);
        Button bt_messenger = (Button) findViewById(R.id.bt_messenger);
        Button bt_aidl = (Button) findViewById(R.id.bt_aidl);
        bt_aidl.setOnClickListener(this);
        bt_messenger.setOnClickListener(this);
        bt_bundle.setOnClickListener(this);
        bt_file.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bt_bundle:
                Intent intent = new Intent(MainActivity.this, BundleDemo.class);
                startActivity(intent);
                break;
            case R.id.bt_file:
                Intent intent1 = new Intent(MainActivity.this, FileSharingDemo.class);
                startActivity(intent1);
                break;
            case R.id.bt_messenger:
                Intent intent2 = new Intent(MainActivity.this, MessengerDemo.class);
                startActivity(intent2);
                break;
            case R.id.bt_aidl:
                Intent intent3 = new Intent(MainActivity.this, MessengerDemo.class);
                startActivity(intent3);
                break;
        }
    }
}
