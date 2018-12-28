package com.example.pan.ipcdemo.Socket;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pan.ipcdemo.R;
import com.example.pan.ipcdemo.uitls.Tuichu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 这是客户端，socket通讯要开启服务端
 * Created by pan on 2018/12/27.
 */

public class LoginSocketDemo extends AppCompatActivity {
    EditText et_chat_user;
    EditText et_chat_password;
    Button bt_chat_login;
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private String content = "";
    private Thread thread;//循环发送心跳包的线程
    private OutputStream outputStream;//输出流，用于发送心跳
    boolean stopThread = false;


    //定义一个handler对象,用来刷新界面
    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                Toast.makeText(LoginSocketDemo.this, "信息错误", Toast.LENGTH_SHORT).show();
            }else if(msg.what == 0x234){
                Toast.makeText(LoginSocketDemo.this, "信息为空，请输入信息", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_login);
        Tuichu.getInstance().addActivity(this);
        et_chat_user = (EditText) findViewById(R.id.et_chat_user);
        et_chat_password = (EditText) findViewById(R.id.et_chat_password);
        bt_chat_login = (Button) findViewById(R.id.bt_chat_login);

        new Thread() {
            public void run() {
                try {
                    socket = new Socket("192.168.0.159", 8888);
                    socket.setKeepAlive(true);
                    startThreadSocket();
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                    out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                            socket.getOutputStream())), true);
                    while (true) {
                        if (socket.isConnected()) {
                            if (!socket.isInputShutdown()) {
                                try {
                                    if (in.readLine().equals("ok")) {
                                        System.out.println("测试: " + content);
                                        finish();
                                        Intent intent = new Intent(LoginSocketDemo.this, ChatSocketDemo.class);
                                        startActivity(intent);
                                        break;
                                    } else {
                                        handler.sendEmptyMessage(0x123);
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        bt_chat_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String password = et_chat_password.getText().toString();
                final String user = et_chat_user.getText().toString();
                final String login = "1" + user + password;
                if (!password.equals("") && !user.equals("")) {
                    if (socket.isConnected()) {
                        if (!socket.isOutputShutdown()) {
                            new Thread() {
                                @Override
                                public void run() {
                                    out.println(login);
                                }
                            }.start();
                        }
                    }
                } else {
                    Toast.makeText(LoginSocketDemo.this, "信息为空，请输入信息", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void startThreadSocket() {
        try {
            if (!socket.getKeepAlive()) socket.setKeepAlive(true);//true，若长时间没有连接则断开
            if (!socket.getOOBInline()) socket.setOOBInline(true);//true,允许发送紧急数据，不做处理
            outputStream = socket.getOutputStream();//获得socket的输出流
            final String socketContent = "[心跳信息]" + "\n";
            thread = new Thread() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(5 * 1000);//20s发送一次心跳
                            outputStream.write(socketContent.getBytes("UTF-8"));
                            outputStream.flush();
                        } catch (Exception e) {
                            System.out.println("连接断开，发送失败");
                            break;
                        }
                    }
                }
            };
            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        if (socket != null) {
            try {
//                socket.shutdownInput();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }
}
