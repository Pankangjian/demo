package com.example.pan.ipcdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;


/**
 * Messenger服务端
 * Created by pan on 2018/12/18.
 */

public class MessengerService extends Service {
    public static Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            // 接受客户端发送的消息
            Log.d("service", "服务端接受到客户端信息:" + msg.getData().getString("msg"));
            // 相应客户端请求
            Messenger replyTo = msg.replyTo;
            Message replyMessage = Message.obtain();
            Bundle bundle = new Bundle();
            String text = "成功";
            bundle.putString("reply",text);
            replyMessage.setData(bundle);
            try {
                replyTo.send(replyMessage);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.d("service", "这里是服务端，回应客户端响应");
            super.handleMessage(msg);
        }
    };
    // 通过handler 构建Mesenger 对象
    private final Messenger messenger = new Messenger(handler);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // 返回binder 对象
        return messenger.getBinder();
    }
}
