package com.example.user.shopkeeper.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.shopkeeper.R;
import com.example.user.shopkeeper.model.Customers;
import com.example.user.shopkeeper.sql.DatabaseHelper;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity  {
    SharedPreferences sp;
    TextView name_of_user;
    TextView user_amount;
    private final AppCompatActivity activity = UserActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

//        initViews();
        Intent extras = getIntent();
        final String email = extras.getStringExtra("Email");
        final String name = extras.getStringExtra("Name");
        final String user_id = extras.getStringExtra("User_Id");
        final String contact_user = extras.getStringExtra("Contact");

        name_of_user = findViewById(R.id.name_of_user);
        user_amount = findViewById(R.id.user_amount);

        name_of_user.setText("Hello " + name);

        FloatingActionButton add_user = findViewById(R.id.floatingActionButton7);
        add_user.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Intent i = new Intent(UserActivity.this, AddNewMember.class);
                    i.putExtra("User_Name",name);
                    i.putExtra("User_Id_Client",user_id);
                    i.putExtra("Contact_User",contact_user);
                    startActivity(i);
                }
        });

        DatabaseHelper db = new DatabaseHelper(activity);

        ArrayList<Customers> customers = new ArrayList<Customers>();
        customers = db.get_Customers(user_id);

        CustomerAdapter adapter = new CustomerAdapter(this,customers);
        final ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent ,View view, int position,long id){
                Customers customer;
                customer = (Customers) listView.getItemAtPosition(position);

                Intent intent = new Intent(UserActivity.this,CustomerDetails.class);
                intent.putExtra("User_Id",user_id);
                intent.putExtra("C_name",customer.getCustomerName());
                intent.putExtra("C_amount",customer.getCustomerAmount());
                intent.putExtra("C_mobile_no",customer.getMobileNo());
                Toast.makeText(getApplicationContext(),user_id,Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });



    }

    @Override
    public void onRestart(){
        super.onRestart();

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.logout:

                sp = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.apply();

                Intent in = new Intent(UserActivity.this,LoginActivity.class);
                startActivity(in);
                finish();
                return true;
        }
        return false;
    }







}
