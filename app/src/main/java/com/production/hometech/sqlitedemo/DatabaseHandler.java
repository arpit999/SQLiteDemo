package com.production.hometech.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arpit on 12-May-17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "contactManager";
    private static final int VERSION = 1;
    private static final String TABLE = "contact";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE = "phone";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE + " (" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_PHONE + " TEXT" + ")";

        sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        throw new RuntimeException("How did we get here?");
    }

    Long addContact(Contact contact) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PHONE, contact.getPhone());

//        INSERT VALUE
        Long row = sqLiteDatabase.insert(TABLE, null, values);
        sqLiteDatabase.close();

        return row;
    }

    public ArrayList<Contact> getAllContacts() {

        ArrayList<Contact> contactList = new ArrayList<>();
//        select all contacts
        String selectQuery = "SELECT * FROM " + TABLE;

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

//        looping through get all contacts

        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(cursor.getInt(0));
                contact.setName(cursor.getString(1));
                contact.setPhone(cursor.getString(2));
//                Adding to contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return contactList;
    }

    public int updateContact(Contact contact) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PHONE, contact.getPhone());

//        updating row
        return database.update(TABLE, values, KEY_ID + "= ?", new String[]{String.valueOf(contact.getId())});

    }

    void deleteContact(Contact contact) {

        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE, KEY_ID + " = ?", new String[]{String.valueOf(contact.getId())});
        database.close();
    }

    int deleteAllRecords() {
        SQLiteDatabase database = this.getWritableDatabase();
        int delete_row = database.delete(TABLE, "1", null);
        database.close();
       return delete_row;
    }


}
