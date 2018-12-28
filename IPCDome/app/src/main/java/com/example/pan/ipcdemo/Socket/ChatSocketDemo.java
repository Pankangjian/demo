package com.example.pan.ipcdemo.Socket;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.pan.ipcdemo.R;
import com.example.pan.ipcdemo.uitls.Tuichu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by pan on 2018/12/26.
 */

public class ChatSocketDemo extends AppCompatActivity {
    //定义相关变量,完成初始化
    private TextView tx_chat_show;
    private EditText et_chat_send;
    private Button bt_chat_send;
    Button bt_connect;
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private String content = "";
    private StringBuilder sb = null;
    private Thread thread;//循环发送心跳包的线程
    private OutputStream outputStream;//输出流，用于发送心跳

    //定义一个handler对象,用来刷新界面
    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {
                // 更新UI
                sb.append(content);
                tx_chat_show.setText(sb.toString());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        sb = new StringBuilder();
        tx_chat_show = (TextView) findViewById(R.id.tx_chat_show);
        et_chat_send = (EditText) findViewById(R.id.et_chat_send);
        bt_chat_send = (Button) findViewById(R.id.bt_chat_send);
        bt_connect = (Button) findViewById(R.id.bt_connect);
        bt_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        finish();
                        Intent intent = new Intent(ChatSocketDemo.this, ChatSocketDemo.class);
                        startActivity(intent);
                    }
                }.start();
            }
        });

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
                                if ((content = in.readLine()) != null) {
                                    if(!content.equals("ok")){
                                        content += "\n";
                                        handler.sendEmptyMessage(0x123);
                                    }
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        //为发送按钮设置点击事件
        bt_chat_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String msg = et_chat_send.getText().toString();
//               final String msg = "pk:123456";
                if (socket.isConnected()) {
                    if (!socket.isOutputShutdown()) {
                        new Thread() {
                            @Override
                            public void run() {
                                out.println(msg);
                            }
                        }.start();
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        System.out.println("销毁");
        final String msg = "再见";
        if (socket.isConnected()) {
            if (!socket.isOutputShutdown()) {
                new Thread() {
                    @Override
                    public void run() {
                        out.println(msg);
                    }
                }.start();
            }
        }
        super.onDestroy();
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

}