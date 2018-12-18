package com.example.pan.ipcdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


/**
 * 先让用户类实现Serializable接口
 * 然后用putSerializable(String key,Serializable value)来存储数据，
 * Serializable getSerializable(String key)来取出数据
 * Created by pan on 2018/12/17.
 */

public class BundleDemo extends Activity {
    EditText et_name;
    EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bundle);
        et_name = (EditText) findViewById(R.id.et_name);
        et_password = (EditText) findViewById(R.id.et_password);
        Button bt_login = (Button) findViewById(R.id.login);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString();
                String password = et_password.getText().toString();
                BundleUser User = new BundleUser();
                User.setName(name);
                User.setPassword(password);
                Bundle bundle = new Bundle();
                // 把用户数据放入到bundle中,"user"的key在取出数据时必须一致
                bundle.putSerializable("user", User);
                Intent intent = new Intent(BundleDemo.this, BundleText.class);
                // 把bundle放入intent里
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });}

}
