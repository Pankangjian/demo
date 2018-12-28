package com.example.pan.ipcdemo.contentProvider;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pan.ipcdemo.BookAidl;
import com.example.pan.ipcdemo.R;
import com.example.pan.ipcdemo.User;

/**
 * Created by pan on 2018/12/19.
 */

public class ContentProviderDemo extends Activity implements View.OnClickListener {
    TextView tv_cp_show;
    TextView tv_cp_user_show;
    Uri bookUri;
    Uri userUri;
    EditText et_cp_input;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
        Button bt_cp_query = (Button) findViewById(R.id.bt_cp_query);
        Button bt_cp_add = (Button) findViewById(R.id.bt_cp_add);
        Button bt_cp_user = (Button) findViewById(R.id.bt_cp_user);
        et_cp_input = (EditText) findViewById(R.id.et_cp_input);
        tv_cp_show = (TextView) findViewById(R.id.tv_cp_show);
        tv_cp_user_show = (TextView) findViewById(R.id.tv_cp_user_show);
        bt_cp_query.setOnClickListener(this);
        bt_cp_add.setOnClickListener(this);
        bt_cp_user.setOnClickListener(this);
        userUri = Uri.parse("content://com.example.pan.ipcdemo.book.provider/user");
        bookUri = Uri.parse("content://com.example.pan.ipcdemo.book.provider/book");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_cp_query:
                tv_cp_show.setText("");
                Cursor bookCursor = getContentResolver().query(bookUri, new String[]{"_id", "name"},
                        null, null, null);
                while (bookCursor.moveToNext()) {
                    BookAidl book = new BookAidl();
                    book.bookId = bookCursor.getInt(0);
                    book.bookName = bookCursor.getString(1);
                    Log.d("cp", "query book:" + book.toString());
                    String str = book.toString();
                    tv_cp_show.append(str);
                }
                bookCursor.close();
                break;
            case R.id.bt_cp_user:
                tv_cp_user_show.setText("");
                Cursor userCursor = getContentResolver().query(userUri, new String[]{"_id", "name", "sex"}, null, null, null);
                while (userCursor.moveToNext()) {
                    User user = new User();
                    user.userId = userCursor.getInt(0);
                    user.userName = userCursor.getString(1);
                    Log.d("cp", "query user:" + user.toString());
                    tv_cp_user_show.append(user.toString());
                }
                userCursor.close();
                break;
            case R.id.bt_cp_add:
                ContentValues values = new ContentValues();
                values.put("_id", 4);
                values.put("name", "开发者艺术");
                getContentResolver().insert(bookUri, values);
                break;
        }
    }
}
