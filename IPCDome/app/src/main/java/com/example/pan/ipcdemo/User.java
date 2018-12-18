package com.example.pan.ipcdemo;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Parcelable实例化
 * Created by pan on 2018/12/17.
 */

public class User implements Parcelable {
    private String name;
    private int password;

    public User(Parcel in) {
        name = in.readString();
        password = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };



    @Override
    public int describeContents() {
        // 返回当前对象的内容描述。几乎所有情况下都是返回0
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // 将当前对象写入到序列化结构中
        dest.writeString(name);
        dest.writeInt(password);
    }

}
