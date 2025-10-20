package com.devstack.pos.entity;

import java.util.Date;

public class OrderDetail {
    private int orderId;
    private String productId;
    private double unitPrice;
    private int qty;
    private Date date;

    public OrderDetail() {
    }

    public OrderDetail(int orderId, String productId, double unitPrice, int qty, Date date) {
        this.orderId = orderId;
        this.productId = productId;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.date = date;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
