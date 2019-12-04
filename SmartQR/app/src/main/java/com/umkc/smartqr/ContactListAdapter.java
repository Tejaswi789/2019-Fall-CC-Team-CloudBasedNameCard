package com.umkc.smartqr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ContactListAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    List<String> contacts;
    ArrayList<String> contactList;

    ContactListAdapter(Context c, List<String> contacts){
        this.context =c;
        this.contacts = contacts;
        this.layoutInflater = LayoutInflater.from(c);
        this.contactList = new ArrayList<String>();
        this.contactList.addAll(contacts);
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.contactlist,null);
        TextView name = (TextView) convertView.findViewById(R.id.txtDisName);
        name.setText((contacts.get(position)).toString());
        return convertView;
    }
}
