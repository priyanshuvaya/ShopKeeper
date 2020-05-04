package com.example.user.shopkeeper.model;

public class Customers {
    private String mCustomerName;
    private String mMobileNo;
    private String mCustomerAmount;

    public Customers(String customerName, String mobileNo, String customerAmount){
        mCustomerName = customerName;
        mCustomerAmount = customerAmount;
        mMobileNo = mobileNo;
    }

    public String getCustomerAmount() {
        return mCustomerAmount;
    }

    public String getMobileNo() {
        return mMobileNo;
    }

    public String getCustomerName() {
        return mCustomerName;
    }

}
