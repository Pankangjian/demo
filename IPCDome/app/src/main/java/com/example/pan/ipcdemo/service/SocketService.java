package com.example.pan.ipcdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * Created by pan on 2018/12/19.
 */

public class SocketService extends Service {

    // tcp连接状态
    private boolean isDestroyed = false;

    private String[] messages = new String[]{
            "你好",
            "你叫什么名字",
            "今天的天气不错啊！",
            "我是AI机器人",
            "拜拜！",
            "你说什么我不懂"
    };

    public SocketService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new TCPServer()).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class TCPServer implements Runnable {

        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                // 监听8688 端口
                serverSocket = new ServerSocket(8688);
                while (!isDestroyed) {
                    // 获取客户端的Socket 对象
                    final Socket client = serverSocket.accept();
                    Log.d("service", "获取到socket对象");
                    new Thread() {
                        @Override
                        public void run() {
                            // 回应客户端
                            responseClient(client);
                        }
                    }.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void responseClient(Socket client) {
        try {
            // 接受客户端信息-- 获取输入流 --
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            // 回应客户端消息 -- 获取输出流 --
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter
                    (client.getOutputStream())), true);
            out.println("欢迎来到聊天室！");
            while (!isDestroyed) {
                // 通过输入流读取客户端的消息
                String line = in.readLine();
                Log.d("service", "客户端留言: " + line);
                if (line == null) break;
                int i = new Random().nextInt(messages.length);
                String message = messages[i];
                out.println(message);
                Log.d("service", "回应客户端: " + message);
            }
            in.close();
            out.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        isDestroyed = true;
        super.onDestroy();
    }
}
