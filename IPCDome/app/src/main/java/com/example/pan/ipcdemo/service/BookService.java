package com.example.pan.ipcdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import com.example.pan.ipcdemo.BookAidl;
import com.example.pan.ipcdemo.IBookManager;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 在Service中，主要干了两件事情：
 * 实现aidl文件中的接口的Stub对象。并实现方法。
 * 将Binder对象通过onBinder返回给客户端
 * Created by pan on 2018/12/18.
 */

public class BookService extends Service {

    // 支持线程同步，因为其存在多个客户端同时连接的情况
    private CopyOnWriteArrayList<BookAidl> list = new CopyOnWriteArrayList<>();

    // 构造aidl中声明的接口的Stub对象，并实现所声明的方法
    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<BookAidl> getBookList() throws RemoteException {
            return list;
        }

        @Override
        public void addBook(BookAidl book) throws RemoteException {
            list.add(book);
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
                               double aDouble, String aString) throws RemoteException {

        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        list.add(new BookAidl(1, "android"));
        list.add(new BookAidl(2, "java"));
        list.add(new BookAidl(3, "c++"));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // 返回给客户端的Binder对象
        return mBinder;
    }
}
