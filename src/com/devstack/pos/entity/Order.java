package com.devstack.pos.entity;

import java.util.Date;

public class Order {
    private int orderId;
    private String customerId;
    private double totalCost;
    private Date date;

    public Order() {
    }

    public Order(int orderId, String customerId, double totalCost, Date date) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.totalCost = totalCost;
        this.date = date;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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
