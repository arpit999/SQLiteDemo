package com.production.hometech.sqlitedemo;

/**
 * Created by Arpit on 14-May-17.
 */

public interface SQLiteListener {

    void valueAdded(Long id, Contact contact);

    void valueUpdate(int position, Contact contact);
}
