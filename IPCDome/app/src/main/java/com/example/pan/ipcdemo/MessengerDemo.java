package com.example.pan.ipcdemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pan.ipcdemo.service.MessengerService;

/**
 * Created by pan on 2018/12/17.
 */

public class MessengerDemo extends Activity {
    Intent intent;
    Messenger mService;
    EditText et_messenger_send;
    TextView tv_messenger_show;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 请求服务端
            // 通过服务端返回的Binder 对象 构造Messenger
            mService = new Messenger(service);
//            Log.d("messenger", "客户端以获取服务端Messenger对象");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        et_messenger_send = (EditText) findViewById(R.id.et_messenger_send);
        tv_messenger_show = (TextView) findViewById(R.id.tv_messenger_show);
        // 启动服务
        intent = new Intent(this, MessengerService.class);
        bindService(intent, conn, BIND_AUTO_CREATE);

        Button bt_send = (Button) findViewById(R.id.bt_send);
        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });
    }

    // 发送消息给服务端
    private void send() {
        String str = et_messenger_send.getText().toString();
        if (!str.equals("")) {
            // 向服务端发送消息
            Message message = Message.obtain();
            Bundle data = new Bundle();
            data.putString("msg", str);
            message.setData(data);

            try {
                // 发送消息
                mService.send(message);
                // 获取服务端回应的Messenger对象
                message.replyTo = messengerClient;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.d("messenger", "这里是客户端，向服务端发送消息");
        } else {
            Toast.makeText(this, "信息为空，请输入信息", Toast.LENGTH_SHORT).show();
        }
    }

    // 接受服务端回应
    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            // 接受服务端发送的消息
            Log.d("messenger", "客户端接受到服务端回应:" + msg.getData().getString("reply"));
            tv_messenger_show.setText(msg.getData().getString("reply"));
            super.handleMessage(msg);
        }
    };
    private final Messenger messengerClient = new Messenger(handler);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn); // 解除绑定
    }
}
