package com.example.appbanbimsua.enitities.request;

public class ChangePasswordRequest {
    private String old_Password;
    private String new_Password;

    // Constructor, getters, and setters
    public ChangePasswordRequest(String oldPassword, String newPassword) {
        this.old_Password = oldPassword;
        this.new_Password = newPassword;
    }

    public String getOldPassword() {
        return old_Password;
    }

    public void setOldPassword(String oldPassword) {
        this.old_Password = oldPassword;
    }

    public String getNewPassword() {
        return new_Password;
    }

    public void setNewPassword(String newPassword) {
        this.new_Password = newPassword;
    }
}
