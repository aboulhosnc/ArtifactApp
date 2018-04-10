package com.example.chady.artifactapp;

import java.util.ArrayList;

/**
 * Created by michaelpoblacion1 on 4/8/18.
 */

public class Users {
    private String name;
    private String email;
    private long phoneNumber;
    private String address;
    private String storeName;
    private String uID;
    private ArrayList<Artifact> userArtifacts;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public ArrayList<Artifact> getUserArtifacts() {
        return userArtifacts;
    }

    public void addUserArtifact(Artifact userArtifact) {
        userArtifacts.add(userArtifact);
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
