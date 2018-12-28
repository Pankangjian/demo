package com.example.pan.ipcdemo.Socket;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pan.ipcdemo.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by pan on 2018/12/25.
 */

public class TCPSocketDemo extends AppCompatActivity implements View.OnClickListener {
    TextView tv_tcp_show;
    EditText et_tcp_chat;
    Button bt_tcp_chat;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_tcp);
        tv_tcp_show = (TextView) findViewById(R.id.tv_tcp_show);
        et_tcp_chat = (EditText) findViewById(R.id.et_tcp_chat);
        Button bt_tcp_send = (Button) findViewById(R.id.bt_tcp_send);
        bt_tcp_chat = (Button) findViewById(R.id.bt_tcp_chat);
        bt_tcp_send.setOnClickListener(this);
        bt_tcp_chat.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_tcp_send:
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            sendServer();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
        }
    }

    private void sendServer() throws IOException {
        //创建Socket对象
        Socket socket = new Socket("192.168.0.159", 8888);
        //根据输入输出流和服务端连接
        OutputStream outputStream = socket.getOutputStream();//获取一个输出流，向服务端发送信息
        PrintWriter printWriter = new PrintWriter(outputStream);//将输出流包装成打印流
        printWriter.print("服务端你好，我是客户端");
        printWriter.flush();
        socket.shutdownOutput();//关闭输出流
        InputStream inputStream = socket.getInputStream();//获取一个输入流，接收服务端的信息
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);//包装成字符流，提高效率
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);//缓冲区
        String info = "";
        String temp = null;//临时变量
        while ((temp = bufferedReader.readLine()) != null) {
            info += temp;
            System.out.println("客户端接收服务端发送信息：" + info);
        }
        //关闭相对应的资源
        bufferedReader.close();
        inputStream.close();
        printWriter.close();
        outputStream.close();
        socket.close();
    }

}
