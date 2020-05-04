package com.example.user.shopkeeper.model;

public class Transaction {
    private String mUser_id;
    private String mCustomer_id;
    private String mTransaction_amount;
    private String mStatus;
    private String mDate;
    private String mSequence;

    public Transaction(){}

    public Transaction(String User_id, String Customer_id,String Transaction_amount,String status){
        mUser_id = User_id;
        mCustomer_id = Customer_id;
        mTransaction_amount = Transaction_amount;
        mStatus = status;
    }

    public Transaction(String User_id, String Customer_id,String Transaction_amount,String status,String date,String sequence){
        mUser_id = User_id;
        mCustomer_id = Customer_id;
        mTransaction_amount = Transaction_amount;
        mStatus = status;
        mDate = date;
        mSequence = sequence;
    }

    public String getUserId(){
        return mUser_id;
    }

    public String getCustomerId(){
        return mCustomer_id;
    }

    public String getTransactionAmount(){
        return mTransaction_amount;
    }

    public String getStatus(){
        return mStatus;
    }

    public String getDate(){
        return mDate;
    }

    public String getSequence(){
        return mSequence;
    }

}
