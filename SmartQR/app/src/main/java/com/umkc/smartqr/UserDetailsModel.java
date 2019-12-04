package com.umkc.smartqr;

public class UserDetailsModel {
    String id;
    String name;
    String phone;
    String email;
    String address;

    public UserDetailsModel() {
    }

    public UserDetailsModel(String id, String name, String phone, String email, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }
}
