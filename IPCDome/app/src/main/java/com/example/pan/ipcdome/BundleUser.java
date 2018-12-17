package com.example.pan.ipcdome;

import java.io.Serializable;

/**
 * Serializable实例化
 * Created by pan on 2018/12/17.
 */

public class BundleUser implements Serializable {
    private static final long serialVersionUID = 660149L;
    private String name;
    private String password;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
