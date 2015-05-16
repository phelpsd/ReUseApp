package com.example.tracy.reuse;

/**
 * Created by Tracy on 4/28/2015.
 */
public class Business {
    private String id;
    private String name;
    private String phone;
    private String website;
    private String address;


    public Business(String id, String name, String phone, String website, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.website = website;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}