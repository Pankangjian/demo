package com.example.pan.ipcdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pan.ipcdemo.service.SocketService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * socket
 * 主要使用 socket.getOutputStream() 和 socket.getInputStream() 方法分别发送、接收服务端消息。
 * Created by pan on 2018/12/19.
 */

public class SocketDemo extends Activity {
    private Button bt_socket_send;
    private TextView tv_socket_show;
    private EditText et_socket_content;
    private PrintWriter mPrintWriter;
    private Socket mClientSocket;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        initView();
        // 开启服务
        Intent intent = new Intent(this, SocketService.class);
        startService(intent);
        new Thread() {
            @Override
            public void run() {
                connectSocketServer();
            }
        }.start();
    }

    private void initView() {
        et_socket_content = (EditText) findViewById(R.id.et_socket_content);
        tv_socket_show = (TextView) findViewById(R.id.tv_socket_show);
        bt_socket_send = (Button) findViewById(R.id.bt_socket_send);
        bt_socket_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String str = et_socket_content.getText().toString();
                //向服务器发送信息
                if (!TextUtils.isEmpty(str) && mPrintWriter != null) {
                    Log.d("客户端", "onClick: " + str);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mPrintWriter.println(str);
                        }
                    }).start();
                    tv_socket_show.setText(tv_socket_show.getText() + "\n" + "客户端：" + str);
                    et_socket_content.setText("");
                }
            }
        });
    }

    private void connectSocketServer() {
        Socket socket = null;
        while (socket == null) {
            try {
                // 选择和服务器相同的端口8688
                socket = new Socket("localhost", 8688);
                mClientSocket = socket;
                mPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter
                        (socket.getOutputStream())), true);
                SystemClock.sleep(1000);
                // 接收服务器端的消息
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (!isFinishing()) {
                    final String str = reader.readLine();
                    if (str != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // 更新UI
                                tv_socket_show.setText(tv_socket_show.getText() +
                                        "\n" + "服务端：" + str);
                            }
                        });
                    }
                }
                mPrintWriter.close();
                reader.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (mClientSocket != null) {
            try {
                // 关闭输入流
                mClientSocket.shutdownInput();
                mClientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }
}
