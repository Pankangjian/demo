package com.example.pan.ipcdome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * 显示类
 * Created by pan on 2018/12/17.
 */

public class BundleText extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bundle_text);
        TextView tv_show = (TextView) findViewById(R.id.tv_show);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        // "user"的key在取出数据时必须与数据存入bundle时的key一致
        BundleUser bundleUser = (BundleUser) bundle.getSerializable("user");
        tv_show.setText("名字：" + bundleUser.getName() + "\n" + "密码：" + bundleUser.getPassword());
    }
}
