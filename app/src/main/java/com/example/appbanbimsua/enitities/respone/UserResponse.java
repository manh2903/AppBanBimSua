package com.example.appbanbimsua.enitities.respone;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class UserResponse {
    @SerializedName("id")
    private int id;

    @SerializedName("fullName")
    private String fullName;

    @SerializedName("email")
    private String email;

    @SerializedName("address")
    private String address;

    @SerializedName("phone")
    private String phone;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("roles")
    private List<String> roles;

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id + "," +
                "\"fullName\":\"" + fullName + "\"," +
                "\"email\":\"" + email + "\"," +
                "\"address\":\"" + address + "\"," +
                "\"phone\":\"" + phone + "\"," +
                "\"avatar\":\"" + avatar + "\"," +
                "\"roles\":" + roles +
                "}";
    }
}
