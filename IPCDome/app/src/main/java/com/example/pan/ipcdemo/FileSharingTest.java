package com.example.pan.ipcdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by pan on 2018/12/17.
 */

public class FileSharingTest extends Activity {
    TextView tv_show_file;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_test);
        tv_show_file = (TextView) findViewById(R.id.tv_show_file);
        recoverFromFile();
    }
    private void recoverFromFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BundleUser user = null;
                File dir = new File(getApplicationContext().getCacheDir().getPath() + "/user/");
                File cachedFile = new File(dir.getPath() + "/usercache");
                if (cachedFile.exists()) {
                    ObjectInputStream objectInputStream = null;
                    try {
                        objectInputStream = new ObjectInputStream(
                                new FileInputStream(cachedFile));
                        user = (BundleUser) objectInputStream.readObject();
                        Log.d("", "名字:" + user.getName());
                        tv_show_file.setText(user.getName());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (objectInputStream != null) {
                                objectInputStream.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }
}
