package com.production.hometech.sqlitedemo;

/**
 * Created by Arpit on 12-May-17.
 */

public class Contact {

    String name,phone;
    int id;

    public Contact(String name, String phone, int id) {
        this.name = name;
        this.phone = phone;
        this.id = id;
    }

    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public Contact() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
