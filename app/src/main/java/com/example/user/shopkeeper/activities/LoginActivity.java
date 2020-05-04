package com.example.user.shopkeeper.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;

import com.example.user.shopkeeper.R;
import com.example.user.shopkeeper.helper.InputValidation;
import com.example.user.shopkeeper.sql.DatabaseHelper;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    public final AppCompatActivity activity = LoginActivity.this;
    private ConstraintLayout constraintLayout;
    private TextInputLayout eT_email;
    private TextInputLayout eT_password;
    private TextInputEditText email;
    private TextInputEditText password;
    private AppCompatButton login;
    private AppCompatTextView sign_up;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Phone = "phoneKey";
    public static final String Email = "emailKey";
    String contact_db = "";
    String name_db = "";
    String user_id_db = "";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        initViews();
        initListeners();
        initObjects();

        if(sharedpreferences.getBoolean("logged",false)){
            Intent accountsIntent = new Intent(activity,UserActivity.class);
            String email_a = sharedpreferences.getString(Email,"");
            intent_passing(email_a);
            accountsIntent.putExtra("Email",email_a);
            accountsIntent.putExtra("Name", name_db);
            Log.d("Name ", name_db);
            accountsIntent.putExtra("User_Id", user_id_db);
            accountsIntent.putExtra("Contact", contact_db);
            startActivity(accountsIntent);
            finish();
        }
    }

    private void initViews(){
        constraintLayout = findViewById(R.id.constraint_layout);
        eT_email = findViewById(R.id.eT_email);
        eT_password = findViewById(R.id.eT_Password);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        sign_up = findViewById(R.id.sign_up);
    }

    private void initListeners(){
        login.setOnClickListener(this);
        sign_up.setOnClickListener(this);
    }

    private void initObjects(){
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.login:
                verifyFromSQLite();
                break;
            case R.id.sign_up:
                Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(register);
                break;
        }
    }

    private void verifyFromSQLite(){
        if(!inputValidation.isInputTextFilled(email,eT_email,getString(R.string.error_message_email))){
            return;
        }
        if(!inputValidation.eT_email(email,eT_email,getString(R.string.error_message_email))){
            return;
        }
        if(!inputValidation.isInputTextFilled(password,eT_password,getString(R.string.error_message_password))){
            return;
        }



        if(databaseHelper.checkUser(email.getText().toString().trim(),password.getText().toString().trim())){

            intent_passing(email.getText().toString().trim());
            Log.d("Contact ", contact_db);
            Log.d("name_db ", name_db);
            Log.d("User Id  ", user_id_db);

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(Phone,contact_db);
            editor.putString(Email,email.getText().toString().trim());
            editor.putBoolean("logged",true).apply();
            editor.apply();

            Intent accountsIntent = new Intent(getApplicationContext(),UserActivity.class);

            String email_a = email.getText().toString().trim();
            accountsIntent.putExtra("Email",email_a );
            accountsIntent.putExtra("Name", name_db);
            accountsIntent.putExtra("User_Id", user_id_db);
            accountsIntent.putExtra("Contact", contact_db);


            emptyInputEditText();
            startActivity(accountsIntent);
            finish();
        }
        else{
            Snackbar.make(findViewById(android.R.id.content),getString(R.string.error_valid_email_password),Snackbar.LENGTH_LONG).show();
        }
    }

    private void emptyInputEditText(){
        email.setText(null);
        password.setText(null);
    }

    private void intent_passing(String email_ab){
        DatabaseHelper db = new DatabaseHelper(LoginActivity.this);
        List<String> contactNumber = db.getContactNumber(email_ab);
        contact_db = contactNumber.get(0);
        name_db = contactNumber.get(1);
        user_id_db = contactNumber.get(2);
    }
}
