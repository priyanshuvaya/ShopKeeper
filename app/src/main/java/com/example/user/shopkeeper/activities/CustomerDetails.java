package com.example.user.shopkeeper.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.shopkeeper.R;
import com.example.user.shopkeeper.model.Customers;
import com.example.user.shopkeeper.model.Transaction;
import com.example.user.shopkeeper.sql.DatabaseHelper;

import java.util.ArrayList;

public class CustomerDetails extends AppCompatActivity {
    TextView name,mobile_no,c_amount;
    String customer_name="";
    String customer_mobile ="";
    String customer_amount = "";
    String user_id = "";
    String cus_id ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        Intent i = getIntent();
        customer_name = i.getStringExtra("C_name");
        customer_mobile = i.getStringExtra("C_mobile_no");
        customer_amount = i.getStringExtra("C_amount");
        user_id = i.getStringExtra("User_Id");

        initViews();
        setDataCustomer();
        DatabaseHelper db = new DatabaseHelper(CustomerDetails.this);
        cus_id = db.getCustomerId(customer_mobile,user_id);

        FloatingActionButton add_user = findViewById(R.id.floatingActionButton7);
        add_user.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustomerDetails.this, AddAmount.class);
                i.putExtra("Customer_Name",customer_name);
                i.putExtra("User_Id",user_id);
                i.putExtra("Contact_User",customer_mobile);
                i.putExtra("Cus_id",cus_id);
                Toast.makeText(getApplicationContext(),cus_id,Toast.LENGTH_SHORT).show();

                startActivity(i);
            }
        });

        ArrayList<Transaction> customerInfo = new ArrayList<Transaction>();
        customerInfo = db.getCustomerDetails(user_id,cus_id);

        TransactionAdapter adapter = new TransactionAdapter(this,customerInfo);
        final ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);

    }

    @Override
    public void onRestart(){
        super.onRestart();
    }

    public void initViews(){
        name = findViewById(R.id.name);
        mobile_no = findViewById(R.id.mobile_no);
        c_amount = findViewById(R.id.c_amount);
    }

    public void setDataCustomer(){
        name.setText(customer_name);
        mobile_no.setText(customer_mobile);
        c_amount.setText(customer_amount);
    }


}
