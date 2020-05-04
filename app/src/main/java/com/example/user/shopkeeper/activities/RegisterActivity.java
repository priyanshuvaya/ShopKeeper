package com.example.user.shopkeeper.activities;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.example.user.shopkeeper.R;
import com.example.user.shopkeeper.helper.InputValidation;
import com.example.user.shopkeeper.model.User;
import com.example.user.shopkeeper.sql.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private final AppCompatActivity activity = RegisterActivity.this;
    private ConstraintLayout constraintLayout;
    private TextInputLayout eT_email;
    private TextInputLayout eT_password;
    private TextInputLayout eT_name;
    private TextInputLayout eT_confirmPassword;
    private TextInputLayout eT_contactNumber;
    private TextInputEditText email;
    private TextInputEditText password;
    private TextInputEditText contactNumber;
    private TextInputEditText confirmPassword;
    private TextInputEditText name;
    private AppCompatTextView login;
    private AppCompatButton register;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        initListeners();
        initObjects();
    }

    private void initViews(){
        constraintLayout = findViewById(R.id.constraint_layout);
        eT_email = findViewById(R.id.eT_email);
        eT_password = findViewById(R.id.eT_password);
        eT_name = findViewById(R.id.eT_name);
        eT_confirmPassword = findViewById(R.id.eT_confirmPassword);
        eT_contactNumber = findViewById(R.id.eT_contactNumber);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        contactNumber = findViewById(R.id.contactNumber);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
    }

    private void initListeners(){
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    private void initObjects(){
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
        user = new User();
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.register:
                postDataToSql();
                break;
            case R.id.login:
                finish();
                break;
        }
    }

    private void postDataToSql(){
        if(!inputValidation.isInputTextFilled(name,eT_name,getString(R.string.error_message_name))){
            return;
        }
        if(!inputValidation.isInputTextFilled(email,eT_email,getString(R.string.error_message_email))){
            return;
        }
        if(!inputValidation.eT_email(email,eT_email,getString(R.string.error_message_email))){
            return;
        }
        if(!inputValidation.isInputTextFilled(confirmPassword,eT_confirmPassword,getString(R.string.error_message_contactNumber))){
            return;
        }
        if(!inputValidation.isInputEditTextMatches(password,confirmPassword,eT_confirmPassword,getString(R.string.error_password_match))){
            return;
        }
        if(!inputValidation.isInputTextFilled(password,eT_password,getString(R.string.error_message_password))){
            return;
        }

        if(!inputValidation.isInputTextFilled(contactNumber,eT_contactNumber,getString(R.string.error_message_contactNumber))){
            return;
        }


        if(!databaseHelper.checkUser(email.getText().toString().trim())){
            user.setName(name.getText().toString().trim());
            user.setPassword(password.getText().toString().trim());
            user.setEmail(email.getText().toString().trim());
            user.setNumber(contactNumber.getEditableText().toString().trim());

            databaseHelper.addUser(user);
            Snackbar.make(findViewById(android.R.id.content),getString(R.string.success_message),Snackbar.LENGTH_LONG).show();
            emptyInputEditText();
        } else{
            Snackbar.make(findViewById(android.R.id.content),getString(R.string.error_email_exists),Snackbar.LENGTH_LONG).show();
        }
    }

    private void emptyInputEditText(){
        name.setText(null);
        email.setText(null);
        password.setText(null);
        confirmPassword.setText(null);
        contactNumber.setText(null);
    }

}
