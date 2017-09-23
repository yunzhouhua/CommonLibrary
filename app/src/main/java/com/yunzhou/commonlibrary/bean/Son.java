package com.yunzhou.commonlibrary.bean;

import java.util.Date;

/**
 * Created by huayunzhou on 2017/9/23.
 */

public class Son {
    private String name;
    private Date birthday;

    public Son() {
    }

    public Son(String name, Date birthday) {
        this.name = name;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "Son{" +
                "name='" + name + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
