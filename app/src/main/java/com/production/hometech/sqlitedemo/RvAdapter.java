package com.production.hometech.sqlitedemo;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Arpit on 13-May-17.
 */

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ContactHolder> {

    ArrayList<Contact> contactList;
    Context context;
    int lastPosition = -1;

    public RvAdapter(ArrayList<Contact> contactList, Context context) {
        this.contactList = contactList;
        this.context = context;
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_raw, parent, false);
        return new ContactHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, int position) {

        holder.tv_name.setText(contactList.get(position).getName());
        holder.tv_pnone.setText(contactList.get(position).getPhone());

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }


    public class ContactHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_name, tv_pnone;
        ImageButton bt_edit, bt_delete;

        public ContactHolder(View itemView) {
            super(itemView);

            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_pnone = (TextView) itemView.findViewById(R.id.tv_phone);
            bt_edit = (ImageButton) itemView.findViewById(R.id.bt_edit);
            bt_delete = (ImageButton) itemView.findViewById(R.id.bt_delete);


            bt_edit.setOnClickListener(this);
            bt_delete.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            switch (view.getId()) {

                case R.id.bt_edit:
                    /*DatabaseHandler handler = new DatabaseHandler(context);
                    Cursor mCursor = handler.rawQuery(
                            "SELECT id  FROM  savedstoriestable WHERE heading= '" + heading + "'", null);*/

                    showDialog(contactList.get(getAdapterPosition()));
                    Toast.makeText(context, "click edit", Toast.LENGTH_SHORT).show();

                    break;

                case R.id.bt_delete:

                    String temp = String.valueOf(contactList.get(getAdapterPosition()).getId());

//                    Toast.makeText(context, "click "+temp+" delete", Toast.LENGTH_SHORT).show();

                    DatabaseHandler handler = new DatabaseHandler(context);
                    handler.deleteContact(contactList.get(getAdapterPosition()));
                    contactList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), contactList.size());
                    Toast.makeText(context, "delete contact successfully", Toast.LENGTH_SHORT).show();

                    break;

            }

        }
    }


    private void showDialog(Contact contact) {

        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = ((MainActivity) context).getFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        Fragment prev = ((MainActivity) context).getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        Bundle args = new Bundle();
        args.putString("mode", "update");
        args.putString("name", contact.getName());
        args.putString("phone", contact.getPhone());
        args.putString("id", String.valueOf(contact.getId()));
        // Create and show the dialog.
        DialogFragment newFragment = UpdateDialog.newInstance();
        newFragment.setArguments(args);
        newFragment.show(ft, "dialog");
    }

    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated

        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


}
