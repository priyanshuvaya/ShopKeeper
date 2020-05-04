package com.example.user.shopkeeper.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.user.shopkeeper.R;
import com.example.user.shopkeeper.model.Customers;
import com.example.user.shopkeeper.model.Transaction;

import java.util.ArrayList;

public class TransactionAdapter extends ArrayAdapter<Transaction>{
    public TransactionAdapter(Context context, ArrayList<Transaction> customerInfo){
        super(context,0,customerInfo);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_customer_transaction_details,parent,false);
        }

        Transaction customerInfo = getItem(position);

        TextView sequence = listItemView.findViewById(R.id.sequence);
        TextView date = listItemView.findViewById(R.id.date);
        TextView amount = listItemView.findViewById(R.id.amount);
        TextView status = listItemView.findViewById(R.id.status);

        sequence.setText(customerInfo.getSequence());
        date.setText(customerInfo.getDate());
        amount.setText(customerInfo.getTransactionAmount());
        status.setText(customerInfo.getStatus());


        return listItemView;
    }

}
