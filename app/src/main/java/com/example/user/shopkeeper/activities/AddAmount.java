package com.example.user.shopkeeper.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.user.shopkeeper.R;
import com.example.user.shopkeeper.helper.InputValidation;
import com.example.user.shopkeeper.model.Transaction;
import com.example.user.shopkeeper.sql.DatabaseHelper;

public class AddAmount extends AppCompatActivity {

    private Spinner mStatusSpinner;
    private InputValidation inputValidation;
    private DatabaseHelper db;
    private Transaction transaction;
    private TextInputLayout amount_layout;
    private TextInputEditText amount;
    private Button button;
    private int mStatus = 0;
    private String user_id,customer_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_amount);

        initViews();
        initObjects();
        setupSpinner();

        Intent intent = getIntent();
        user_id = intent.getStringExtra("User_Id");
        customer_id = intent.getStringExtra("Cus_id");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int amount = verifyFromSqlite();

            }
        });


    }

    public void initViews(){
        mStatusSpinner = findViewById(R.id.spinner_amount);
        amount_layout = findViewById(R.id.amount_layout);
        amount = findViewById(R.id.amount);
        button = findViewById(R.id.button);
    }

    public void initObjects(){
        inputValidation = new InputValidation(AddAmount.this);
        db = new DatabaseHelper(AddAmount.this);
        transaction = new Transaction();
    }

    private void setupSpinner() {

        ArrayAdapter statusSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_status_options, android.R.layout.simple_spinner_item);

        statusSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mStatusSpinner.setAdapter(statusSpinnerAdapter);

        mStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.due))) {
                        mStatus = 1; // Male
                    } else if (selection.equals(getString(R.string.extra))) {
                        mStatus = 2; // Female
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mStatus = 1; // Unknown
            }
        });
    }

    private int verifyFromSqlite(){
        if(!inputValidation.isInputTextFilled(amount,amount_layout,getString(R.string.error_amount_empty))){
            return 0;
        }

        transaction = new Transaction(user_id,customer_id,amount.getText().toString().trim(),String.valueOf(mStatus));
        int a = db.addTransaction(transaction);
        Log.d("Amount ", String.valueOf(a));
        return a;
    }

}
