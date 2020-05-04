package com.example.user.shopkeeper.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.user.shopkeeper.R;
import com.example.user.shopkeeper.model.Customers;

import java.util.ArrayList;

public class CustomerAdapter extends ArrayAdapter<Customers> {

    public CustomerAdapter(Context context, ArrayList<Customers> customers){
        super(context,0,customers);
    }

    @Override
    public View getView(int position,View convertView, ViewGroup parent){

        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_customer,parent,false);
        }

        Customers currentCustomer = getItem(position);

        TextView customer_name = listItemView.findViewById(R.id.customer_name);
        TextView customer_contact = listItemView.findViewById(R.id.customer_contact);
        TextView customer_amount = listItemView.findViewById(R.id.customer_amount);

        customer_name.setText(currentCustomer.getCustomerName());
        customer_contact.setText(currentCustomer.getMobileNo());
        customer_amount.setText(currentCustomer.getCustomerAmount());


        return listItemView;
    }



}
