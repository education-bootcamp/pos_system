package com.devstack.pos.dto.request;

public class RequestUserDTO {
    private String email;
    private String displayName;
    private String contactNumber;
    private String password;

    public RequestUserDTO() {
    }

    public RequestUserDTO(String email, String displayName, String contactNumber, String password) {
        this.email = email;
        this.displayName = displayName;
        this.contactNumber = contactNumber;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
