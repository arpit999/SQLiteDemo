package com.production.hometech.sqlitedemo;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Arpit on 14-May-17.
 */

public class AddDialog extends DialogFragment implements View.OnClickListener {

    int mNum;
    EditText et_name, et_phone;
    SQLiteListener sqLiteListener;

    public void setValueAddedListener(SQLiteListener sqLiteListener){
        this.sqLiteListener = sqLiteListener;
    }

    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    public static AddDialog newInstance() {

        Bundle args = new Bundle();

        AddDialog fragment = new AddDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            dialog.getWindow().
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog, container, false);

        Button bt_cancle = (Button) v.findViewById(R.id.bt_cancle);
        Button bt_add = (Button) v.findViewById(R.id.bt_add);

        et_name = (EditText) v.findViewById(R.id.et_name);
        et_phone = (EditText) v.findViewById(R.id.et_phone);

        et_name.setText("");
        et_phone.setText("");

        bt_cancle.setOnClickListener(this);
        bt_add.setOnClickListener(this);


        return v;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.bt_add:

                DatabaseHandler handler = new DatabaseHandler(getActivity());
                Contact contact = new Contact(et_name.getText().toString(), et_phone.getText().toString());
                Long row = handler.addContact(contact);
                if (sqLiteListener!=null)
                    sqLiteListener.valueAdded(row,contact);
                Toast.makeText(getActivity(), "Row id " + row, Toast.LENGTH_SHORT).show();
                getDialog().dismiss();

                break;
            case R.id.bt_cancle:

                getDialog().dismiss();

                break;

        }

    }
}
