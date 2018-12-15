package com.example.pan.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("A", "1.onCreate");
        Log.v("A","error");
        Log.d("A","error");
        Log.w("A","error");
        Log.e("A","error");
        Button button = (Button) findViewById(R.id.bt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("A", "2.onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("A", "6.onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("A", "3.onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("A", "4.onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("A", "5.onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("A", "7.onDestroy");
    }
}
