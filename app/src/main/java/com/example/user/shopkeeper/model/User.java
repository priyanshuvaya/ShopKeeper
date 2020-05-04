package com.example.user.shopkeeper.model;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String number;
    private String userId;
    private int gender;
    private int amount = 0;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getNumber(){
        return number;
    }

    public void setNumber(String number){
        this.number = number;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getUserId(){
        return userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public int getGender(){
        return gender;
    }

    public void setGender(int gender){
        this.gender = gender;
    }

    public int getAmount(){ return amount;}



}
