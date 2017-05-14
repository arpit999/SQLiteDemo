package com.production.hometech.sqlitedemo;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SQLiteListener{

    RecyclerView recyclerView;
    int mStackLevel = 0;
    RvAdapter rvAdapter;
    ArrayList<Contact> contacts;

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:

                showDialog();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        AddDialog dialog =new AddDialog();
        dialog.setValueAddedListener(this);
        DatabaseHandler handler = new DatabaseHandler(this);
       int records =  handler.deleteAllRecords();
        System.out.println(records+" Records deleted ");
        /**
         * CRUD Operations
         * */
        // Inserting Contacts
        Log.d("Insert: ", "Inserting ..");
        handler.addContact(new Contact("Ravi", "9100000000"));
        handler.addContact(new Contact("Srinivas", "9199999999"));
        handler.addContact(new Contact("Tommy", "9522222222"));
        handler.addContact(new Contact("Karthik", "9533333333"));
        handler.addContact(new Contact("Ram", "95222555555"));


        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        contacts = handler.getAllContacts();

        for (Contact cn : contacts) {
            String log = "Id: " + cn.getId() + " ,Name: " + cn.getName() + " ,Phone: " + cn.getPhone();
            // Writing Contacts to log
            Log.d("Name: ", log);

        }

        if(!contacts.isEmpty()) {
            rvAdapter = new RvAdapter(contacts, MainActivity.this);
            recyclerView.setAdapter(rvAdapter);
        } else {
            Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void valueAdded(Long id, Contact contact) {
        System.out.println("value added called");
        if (id == -1) {
            Toast.makeText(this, "value not insert", Toast.LENGTH_SHORT).show();
        } else {

            contacts.add(contact);
            rvAdapter.notifyDataSetChanged();
        }
    }

    void showDialog() {

        mStackLevel++;

        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        Bundle args = new Bundle();
        args.putString("mode", "update");

        // Create and show the dialog.
        DialogFragment newFragment = AddDialog.newInstance();
        newFragment.setArguments(args);
        newFragment.show(ft, "dialog");
    }


}
