package com.devstack.pos.dto.response;

public class ResponseUserDTO {
    private String displayName;
    private String contactNumber;

    private int statusCode;
    private boolean status;
    private String msg;

    public ResponseUserDTO() {
    }

    public ResponseUserDTO(String displayName, String contactNumber, int statusCode, boolean status, String msg) {
        this.displayName = displayName;
        this.contactNumber = contactNumber;
        this.statusCode = statusCode;
        this.status = status;
        this.msg = msg;
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

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
