package com.example.user.shopkeeper.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.user.shopkeeper.model.Customers;
import com.example.user.shopkeeper.model.Transaction;
import com.example.user.shopkeeper.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "manager.db";
        private static final String TABLE_USER = "user";

        // For table maintaining users
        private static final String COLUMN_USER_ID = "user_id";
        private static final String COLUMN_USER_NAME = "user_name";
        private static final String COLUMN_USER_EMAIL = "user_email";
        private static final String COLUMN_USER_PASSWORD = "user_password";
        private static final String COLUMN_USER_MOBILE_NUMBER = "user_mobile_number";



    //Creation of Users Table
        private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                    + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_NAME + " TEXT, "
                    + COLUMN_USER_EMAIL + " TEXT, " + COLUMN_USER_PASSWORD + " TEXT, "+  COLUMN_USER_MOBILE_NUMBER + " TEXT" + ")";
        //

        private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;


        //For tab1el maintaining user's clients
        private final static String TABLE_CLIENT = "clients";
        private final static String _ID = BaseColumns._ID;
        private final static String CLIENT_USER_ID = "client_user_id";
        private final static String COLUMN_CLIENT_NAME = "name";
        private final static String COLUMN_CLIENT_GENDER = "gender";
        private final static String COLUMN_CLIENT_MOBILE_NUMBER = "mobile_number";
        private final static String COLUMN_AMOUNT = "amount";
        public final static int GENDER_UNKNOWN = 0;
        public final static int GENDER_MALE = 1;
        public final static int GENDER_FEMALE = 2;

        private String CREATE_CLIENT_TABLE = "CREATE TABLE " + TABLE_CLIENT + "( "
                + CLIENT_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_ID + " INTEGER, "
                + COLUMN_CLIENT_NAME + " TEXT, " + COLUMN_CLIENT_MOBILE_NUMBER + " TEXT, " + COLUMN_CLIENT_GENDER + " TEXT, "
                + COLUMN_AMOUNT + " INTEGER" + " )";


        // Drop Table on Upgrade Section
        private String DROP_CLIENT_TABLE = "DROP TABLE IF EXISTS " + TABLE_CLIENT;

        private final static String TABLE_TRANSACTION = "client_transactions";
        private final static String TRANSACTION_ID = "id";
        private final static String TRANSACTION_AMOUNT = "amount";
        private final static String TRANSACTION_DATE_TIME = "transaction_date_time";
        private final static String DUE_OR_EXTRA = "status";
        public final static int STATUS_UNKNOWN = 0;
        public final static int STATUS_DUE = 1;
        public final static int STATUS_EXTRA = 2;

        private String CREATE_TRANSACTION_TABLE = "CREATE TABLE " + TABLE_TRANSACTION + "("
                + TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_ID + " INTEGER, "
                + CLIENT_USER_ID + " INTEGER, " + TRANSACTION_AMOUNT + " TEXT, " + TRANSACTION_DATE_TIME + " TEXT, "
                + DUE_OR_EXTRA  + " TEXT" + ")";



        private String DROP_CLIENT_TRANSACTION_TABLE = "DROP TABLE IF EXISTS " + TABLE_TRANSACTION;

        public DatabaseHelper(Context context){
            super(context, DATABASE_NAME,null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(CREATE_USER_TABLE);
            db.execSQL(CREATE_CLIENT_TABLE);
            db.execSQL(CREATE_TRANSACTION_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL(CREATE_TRANSACTION_TABLE);
        }

        public void addUser(User user){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_NAME,user.getName());
            values.put(COLUMN_USER_EMAIL,user.getEmail());
            values.put(COLUMN_USER_PASSWORD,user.getPassword());
            values.put(COLUMN_USER_MOBILE_NUMBER,user.getNumber());

            db.insert(TABLE_USER,null,values);
            db.close();
        }

        public boolean checkUser(String email, String password){
            String[] columns = {
                    COLUMN_USER_ID
            };

            SQLiteDatabase db = this.getWritableDatabase();
            String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ? ";
            String[] selectionArgs = {email , password};

            Cursor cursor = db.query(TABLE_USER,
                    columns,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null );

            int cursorCount = cursor.getCount();
            cursor.close();
            db.close();

            if(cursorCount > 0){
                return true;
            }
            else{
                return false;
            }

        }

        public boolean checkUser(String email){
            String[] columns = {
                    COLUMN_USER_ID
            };

            SQLiteDatabase db = this.getWritableDatabase();
            String selection = COLUMN_USER_EMAIL + " = ?" ;
            String[] selectionArgs = {email};

            Cursor cursor = db.query(TABLE_USER,
                    columns,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null );

            int cursorCount = cursor.getCount();
            cursor.close();
            db.close();

            if(cursorCount > 0){
                return true;
            }
            else{
                return false;
            }

        }

        public List<String> getContactNumber(String email){
            List<String> a = new ArrayList<>();
            String[] contact = new String[]{email};
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT " + COLUMN_USER_MOBILE_NUMBER +", " + COLUMN_USER_ID + ", " +COLUMN_USER_NAME + " FROM " + TABLE_USER + " WHERE " + COLUMN_USER_EMAIL + " =?";
            Cursor c = db.rawQuery(query, contact);

            if(c.moveToFirst()){
                a.add(c.getString(c.getColumnIndex(COLUMN_USER_MOBILE_NUMBER)));
                a.add(c.getString(c.getColumnIndex(COLUMN_USER_NAME)));
                a.add(c.getString(c.getColumnIndex(COLUMN_USER_ID)));

            }

            db.close();
            c.close();
            return a;
        }

        public boolean checkUserMobile(String contact,String user_id){
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT *" + " FROM " + TABLE_CLIENT + " WHERE " + COLUMN_CLIENT_MOBILE_NUMBER + "=" + contact + " AND " + COLUMN_USER_ID + " = " + user_id;
            Cursor d = db.rawQuery(query,null);
            int count = d.getCount();
            d.close();
            db.close();

            if(count > 0){
                return true;
            } else{
                return false;
            }
        }

        public void addClient(User user){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_ID,user.getUserId());
            values.put(COLUMN_CLIENT_NAME,user.getName());
            values.put(COLUMN_CLIENT_MOBILE_NUMBER,user.getNumber());
            values.put(COLUMN_CLIENT_GENDER,user.getGender());
            values.put(COLUMN_AMOUNT,user.getAmount());

            db.insert(TABLE_CLIENT,null,values);
            db.close();
        }

        public ArrayList<Customers> get_Customers(String user_id){
            ArrayList<Customers> customer = new ArrayList<Customers>();

            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT * FROM " + TABLE_CLIENT + " WHERE " + COLUMN_USER_ID + " = " + user_id;
            Cursor cursor = db.rawQuery(query,null);

            if(cursor.getCount() == 0){
                return customer;
            }
            else{
                cursor.moveToFirst();
                do{
                    String contact = cursor.getString(cursor.getColumnIndex(COLUMN_CLIENT_MOBILE_NUMBER));
                    String name = cursor.getString(cursor.getColumnIndex(COLUMN_CLIENT_NAME));
                    String amount = cursor.getString(cursor.getColumnIndex(COLUMN_AMOUNT));

                    customer.add(new Customers(name,contact,amount));

                }while(cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return customer;

        }

        public String getCustomerId(String mobile, String user_id){
            String client_id = "";
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT * FROM " + TABLE_CLIENT + " WHERE " + COLUMN_CLIENT_MOBILE_NUMBER + " = " + mobile + " AND " + COLUMN_USER_ID + " = " + user_id;
            Cursor cursor = db.rawQuery(query,null);
            cursor.moveToFirst();
            if(cursor.getCount()>0){
                do{
                    client_id = cursor.getString(cursor.getColumnIndex(CLIENT_USER_ID));
                }while(cursor.moveToNext());
            }

            return client_id;
        }

        public ArrayList<Transaction> getCustomerDetails(String user_id, String customerId){
            ArrayList<Transaction> customerInfo = new ArrayList<Transaction>();
            SQLiteDatabase db = this.getReadableDatabase();
            String[] columns = new String[]{user_id,customerId};
            String query = "SELECT * FROM " + TABLE_TRANSACTION + " WHERE " + COLUMN_USER_ID + " = " + user_id +  " AND " + CLIENT_USER_ID + " = " + customerId;
            Cursor cursor = db.rawQuery(query,null);
            int a=1;
            if(cursor.getCount() == 0){
                return customerInfo;
            }
            else{
                cursor.moveToFirst();
                do{
                    String userId,cusId,amount,status,date,sequence;
                    userId = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID));
                    cusId = cursor.getString(cursor.getColumnIndex(CLIENT_USER_ID));
                    amount = cursor.getString(cursor.getColumnIndex(TRANSACTION_AMOUNT));
                    status = cursor.getString(cursor.getColumnIndex(DUE_OR_EXTRA));
                    if(Integer.parseInt(status) == 1){
                        status = "Due";
                    }else if(Integer.parseInt(status) == 2){
                        status = "Extra";
                    }
                    date = cursor.getString(cursor.getColumnIndex(TRANSACTION_DATE_TIME));
                    sequence = String.valueOf(a);
                    a++;

                    customerInfo.add(new Transaction(userId,cusId,amount,status,date,sequence));

                }while(cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return customerInfo;

        }

        public int addTransaction(Transaction transaction){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_ID,transaction.getUserId());
            values.put(CLIENT_USER_ID,transaction.getCustomerId());
            values.put(TRANSACTION_AMOUNT,transaction.getTransactionAmount());
            values.put(DUE_OR_EXTRA,transaction.getStatus());
            values.put(TRANSACTION_DATE_TIME,getDateTime());

            db.insert(TABLE_TRANSACTION,null,values);


            String user_id = transaction.getUserId();
            String customerId = transaction.getCustomerId();


            String query = "SELECT * FROM " + TABLE_TRANSACTION + " WHERE " + CLIENT_USER_ID + " = " + customerId;
            Cursor cursor = db.rawQuery(query,null);
            int amount = 0;
            if(cursor.getCount() == 0){
                return 0;
            }
            else{
                cursor.moveToFirst();
                do{
                    String userId = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID));
                    if(Integer.parseInt(user_id) == Integer.parseInt(userId)){
                        String status = cursor.getString(cursor.getColumnIndex(DUE_OR_EXTRA));
                        int a = Integer.parseInt(cursor.getString(cursor.getColumnIndex(TRANSACTION_AMOUNT)));
                        if(Integer.parseInt(status) == 1){
                            amount += a;
                        }else if(Integer.parseInt(status) == 2){
                            amount -= a;
                        }

                    }
                }while(cursor.moveToNext());
            }
            Log.d("Amount ",String.valueOf(amount));
            ContentValues d = new ContentValues();
            d.put(COLUMN_AMOUNT,amount);
            String selection = COLUMN_USER_ID + " = ? " + " AND " + CLIENT_USER_ID + " = ?";
            String[] selectionArgs = {user_id,customerId};
            db.update(TABLE_CLIENT,d,selection,selectionArgs);

            db.close();
            cursor.close();
            return amount;

        }

        private String getDateTime() {
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date date = new Date();
            return dateFormat.format(date);
        }


}
