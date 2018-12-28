package com.example.pan.ipcdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by pan on 2018/12/17.
 */

public class FileSharingDemo extends Activity {
    EditText et_get_text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        et_get_text = (EditText) findViewById(R.id.et_get_text);
        Button bt_file = (Button) findViewById(R.id.bt_file_1);
        bt_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                persistToFile();
                Intent intent = new Intent(FileSharingDemo.this, FileSharingTest.class);
                startActivity(intent);
            }
        });
    }

    private void persistToFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BundleUser user = new BundleUser();
                String s = et_get_text.getText().toString();
                user.setName(s);
                user.setPassword("660149");
                File dir = new File(getApplicationContext().getCacheDir().getPath() + "/user/");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File cachedFile = new File(dir.getPath() + "/usercache");
                ObjectOutputStream objectOutputStream = null;
                try {
                    objectOutputStream = new ObjectOutputStream(new FileOutputStream(cachedFile));
                    objectOutputStream.writeObject(user);
                    System.out.print("名字=" + user.getName());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (objectOutputStream != null) {
                            objectOutputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
