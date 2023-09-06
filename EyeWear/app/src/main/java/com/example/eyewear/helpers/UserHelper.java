package com.example.eyewear.helpers;

public class UserHelper {

    String name, phone, email, address, pincode;

    public UserHelper() {
    }

    public UserHelper(String name, String phone, String email, String address, String pincode) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.pincode = pincode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}

