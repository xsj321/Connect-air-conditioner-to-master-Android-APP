package com.example.amber.smartapp;

/**
 * Created by amber on 2019/5/30.
 */
public class InfoBean {
    private  static String account;
    private  static  String password;

    private String temp;
    private int  humity;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public int getHumity() {
        return humity;
    }

    public void setHumity(int humity) {
        this.humity = humity;
    }
}
