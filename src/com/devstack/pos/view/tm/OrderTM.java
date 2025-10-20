package com.devstack.pos.view.tm;

import java.util.Date;

public class OrderTM {
    private int id;
    private String customer;
    private double totalCost;
    private Date date;

    public OrderTM() {
    }

    public OrderTM(int id, String customer, double totalCost, Date date) {
        this.id = id;
        this.customer = customer;
        this.totalCost = totalCost;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
