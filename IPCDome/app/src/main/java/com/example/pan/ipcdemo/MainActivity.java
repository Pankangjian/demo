package com.example.pan.ipcdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pan.ipcdemo.Socket.LoginSocketDemo;
import com.example.pan.ipcdemo.Socket.TCPSocketDemo;
import com.example.pan.ipcdemo.Socket.ChatSocketDemo;
import com.example.pan.ipcdemo.contentProvider.ContentProviderDemo;
import com.example.pan.ipcdemo.uitls.Tuichu;

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
        Button bt_cp = (Button) findViewById(R.id.bt_cp);
        Button bt_socket = (Button) findViewById(R.id.bt_socket);
        Button bt_socket_tcp = (Button) findViewById(R.id.bt_socket_tcp);
        Button bt_login = (Button) findViewById(R.id.bt_login);
        bt_login.setOnClickListener(this);
        bt_socket_tcp.setOnClickListener(this);
        bt_socket.setOnClickListener(this);
        bt_cp.setOnClickListener(this);
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
                Intent intent3 = new Intent(MainActivity.this, AidlDemo.class);
                startActivity(intent3);
                break;
            case R.id.bt_cp:
                Intent intent4 = new Intent(MainActivity.this, ContentProviderDemo.class);
                startActivity(intent4);
                break;
            case R.id.bt_socket:
                Intent intent5 = new Intent(MainActivity.this, com.example.pan.ipcdemo.SocketDemo.class);
                startActivity(intent5);
                break;
            case R.id.bt_socket_tcp:
                Intent intent6 = new Intent(MainActivity.this, TCPSocketDemo.class);
                startActivity(intent6);
                break;
            case R.id.bt_login:
                Intent intent7 = new Intent(MainActivity.this, LoginSocketDemo.class);
                startActivity(intent7);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
