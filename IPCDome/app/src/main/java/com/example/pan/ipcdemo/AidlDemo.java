package com.example.pan.ipcdemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pan.ipcdemo.service.BookService;

/**
 * Created by pan on 2018/12/18.
 */

public class AidlDemo extends Activity implements View.OnClickListener {
    EditText et_book_add;
    TextView tv_book_show;
    int a = 3;
    // 接口对象
    private IBookManager mService;

    // 绑定服务的回调
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 获取到书籍管理接口的对象
            mService = IBookManager.Stub.asInterface(service);
            Log.d("aidl", "连接到服务端，获取IBookManager的对象");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl_book);
        et_book_add = (EditText) findViewById(R.id.et_book_add);
        tv_book_show = (TextView) findViewById(R.id.tv_book_show);
        Button bt_book_show = (Button) findViewById(R.id.bt_book_show);
        Button bt_book_add = (Button) findViewById(R.id.bt_book_add);
        bt_book_show.setOnClickListener(this);
        bt_book_add.setOnClickListener(this);

        // 开启绑定服务
        Intent intent = new Intent(this, BookService.class);
        bindService(intent, conn, BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_book_show:
                try {
                    tv_book_show.setText(mService.getBookList().toString());
                    Log.d("aidl", "书籍列表" + mService.getBookList().toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.bt_book_add:
                try {
                    String str = et_book_add.getText().toString();
                    if (!str.equals("")) {
                        a++;
                        mService.addBook(new BookAidl(a, str));
                        Log.d("aidl", "添加了一本书" + new BookAidl(a, str).toString());
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
