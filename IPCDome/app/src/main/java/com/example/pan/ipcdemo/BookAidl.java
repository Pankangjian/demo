package com.example.pan.ipcdemo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pan on 2018/12/18.
 */

public class BookAidl implements Parcelable {
    public int bookId;
    public String bookName;

    public BookAidl() {
        super();
    }

    public BookAidl(int bookId, String bookName) {
        this.bookId = bookId;
        this.bookName = bookName;
    }

    public BookAidl(Parcel in) {
        bookId = in.readInt();
        bookName = in.readString();
    }

    public static final Creator<BookAidl> CREATOR = new Creator<BookAidl>() {
        @Override
        public BookAidl createFromParcel(Parcel in) {
            return new BookAidl(in);
        }

        @Override
        public BookAidl[] newArray(int size) {
            return new BookAidl[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookId);
        dest.writeString(bookName);
    }

    @Override
    public String toString() {
        return "(书号:" +
                bookId + ", " + "书名=" + "\"" + bookName + "\""+")"
                ;
    }
}
