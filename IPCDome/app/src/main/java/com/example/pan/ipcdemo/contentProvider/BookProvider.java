package com.example.pan.ipcdemo.contentProvider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by pan on 2018/12/19.
 */

public class BookProvider extends ContentProvider {
    private Context context;
    private SQLiteDatabase db;

    // 在AndroidManifest中声明的权限
    public static final String AUTHORITY = "com.example.pan.ipcdemo.book.provider";

    // 为book，user指定uri
    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/book");
    public static final Uri USER_CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/user");

    // 与book，user关联
    public static final int BOOK_URI_CODE = 0;
    public static final int USER_URI_CODE = 1;
    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        matcher.addURI(AUTHORITY, "book", BOOK_URI_CODE);
        matcher.addURI(AUTHORITY, "user", USER_URI_CODE);
    }

    private String getTableName(Uri uri) {
        String tableName = null;
        switch (matcher.match(uri)) {
            case BOOK_URI_CODE:
                tableName = DBHelper.BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tableName = DBHelper.USER_TABLE_NAME;
                break;
            default:
                break;
        }
        return tableName;
    }

    public BookProvider() {
    }

    @Override
    public boolean onCreate() {
        Log.d("cp", "onCreate: " + Thread.currentThread());
        context = getContext();
        initProviderData();
        return true;
    }

    private void initProviderData() {
        db = new DBHelper(context).getWritableDatabase();
        db.execSQL("delete from " + DBHelper.BOOK_TABLE_NAME);
        db.execSQL("delete from " + DBHelper.USER_TABLE_NAME);
        db.execSQL("insert into book values(1,'Android');");
        db.execSQL("insert into book values(2,'Ios');");
        db.execSQL("insert into book values(3,'Html5');");
        db.execSQL("insert into user values(1,'路人甲',1);");
        db.execSQL("insert into user values(2,'路人乙',0);");
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d("cp", "query: " + Thread.currentThread());
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException("Unsupported URI:" + uri);
        }
        return db.query(table, projection, selection, selectionArgs, null, null, sortOrder, null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.d("cp", "getType");
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d("cp", "insert");
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException("Unsupported URI:" + uri);
        }
        db.insert(table, null, values);
        // 数据发生改变，通过ContentResolver的notifyChange方法通知外界
        context.getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d("cp", "delete");
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException("Unsupported URI:" + uri);
        }
        int count = db.delete(table, selection, selectionArgs);
        if (count > 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d("cp", "update");
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException("Unsupported URI:" + uri);
        }
        int row = db.update(table, values, selection, selectionArgs);
        if (row > 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return row;
    }
}
