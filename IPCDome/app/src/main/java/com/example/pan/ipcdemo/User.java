package com.example.pan.ipcdemo;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Parcelable实例化
 * Created by pan on 2018/12/17.
 */

public class User implements Parcelable {
    public int userId;
    public String userName;

    public User() {
        super();
    }

    public User(int bookId, String bookName) {
        this.userId = bookId;
        this.userName = bookName;
    }

    public User(Parcel in) {
        userId = in.readInt();
        userName = in.readString();
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
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeString(userName);
    }

    @Override
    public String toString() {
        return "(编号:" +
                userId + ", " + "姓名=" + "\"" + userName + "\"" + ")"
                ;
    }
}
