package com.example.user.shopkeeper.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.user.shopkeeper.R;
import com.example.user.shopkeeper.helper.InputValidation;
import com.example.user.shopkeeper.model.User;
import com.example.user.shopkeeper.sql.DatabaseHelper;

public class AddNewMember extends AppCompatActivity {

    String user_id;
    private Context context;
    User user;
    public final AppCompatActivity activity = AddNewMember.this;
    private TextInputLayout add_client_name;
    private TextInputEditText client_name;
    private TextInputLayout add_client_number;
    private TextInputEditText client_number;
    private InputValidation inputValidation;
    private DatabaseHelper clientUser;

    private Spinner mGenderSpinner;
    private int mGender = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_member);

        initViews();
        initObjects();

        setupSpinner();
    }

    public void initViews(){
        mGenderSpinner = findViewById(R.id.spinner_gender);
        add_client_name  =findViewById(R.id.add_client_name);
        client_name = findViewById(R.id.client_name);
        add_client_number = findViewById(R.id.add_client_number);
        client_number = findViewById(R.id.client_number);

        Intent intent = getIntent();
        user_id = intent.getStringExtra("User_Id_Client");

    }


    public void initObjects(){

        clientUser = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
        user = new User();
    }


    private void setupSpinner() {

        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mGenderSpinner.setAdapter(genderSpinnerAdapter);

        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {
                        mGender = DatabaseHelper.GENDER_MALE; // Male
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        mGender = DatabaseHelper.GENDER_FEMALE; // Female
                    } else {
                        mGender = DatabaseHelper.GENDER_UNKNOWN; // Unknown
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = 0; // Unknown
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_add_client,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_save:
                hideKeyboard(this);
                checkValuesAndInsert();
                return true;
        }
        return false;
    }

    public void checkValuesAndInsert(){
        if(!inputValidation.isInputTextFilled(client_name,add_client_name,getString(R.string.error_message_name))){
            return;
        }

        if(!inputValidation.isInputTextFilled(client_number,add_client_number,getString(R.string.error_message_contactNumber))){
            return;
        }

//        if(!inputValidation.contactNumber(client_number,add_client_number,getString(R.string.error_message_contactNumber))){
//            return;
//        }


        if(!clientUser.checkUserMobile(client_number.getText().toString().trim(),user_id)){
            user.setName(client_name.getEditableText().toString().trim());
            user.setNumber(client_number.getEditableText().toString().trim());
            user.setUserId(user_id);
            user.setGender(mGender);

            clientUser.addClient(user);
            Snackbar.make(findViewById(android.R.id.content),getString(R.string.success_message),Snackbar.LENGTH_LONG).show();
            emptyInputEditText();
        }
        else{
            Snackbar.make(findViewById(android.R.id.content),getString(R.string.error_client_exists),Snackbar.LENGTH_LONG).show();
        }
    }

    private void emptyInputEditText(){
        client_name.setText(null);
        client_number.setText(null);
        mGender = DatabaseHelper.GENDER_UNKNOWN; // Unknown
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
