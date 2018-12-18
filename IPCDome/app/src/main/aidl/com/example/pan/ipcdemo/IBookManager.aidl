// IBookManager.aidl
/**
  在 .aidl 文件中支持的数据类型包括：
  基本数据类型
  String 和 CharSequence
  List:只支持 ArrayList , 里面的元素都必须被 AIDL 支持
  Map:只支持 HashMap , 里面的元素必须被 AIDL 支持
  实现 Parcelable 接口的对象
  所有 AIDL 接口
*/
package com.example.pan.ipcdemo;

// Declare any non-default types here with import statements
import com.example.pan.ipcdemo.BookAidl;
interface IBookManager {
    List<BookAidl> getBookList();
    void addBook(in BookAidl book);
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}
