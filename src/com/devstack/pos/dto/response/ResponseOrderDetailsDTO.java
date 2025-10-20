package com.devstack.pos.dto.response;

public class ResponseOrderDetailsDTO {
    private int orderId;
    private String productId;
    private String productName;
    private int qty;
    private double unitPrice;
    private double totalCost;

    public ResponseOrderDetailsDTO() {
    }

    public ResponseOrderDetailsDTO(int orderId, String productId, String productName, int qty, double unitPrice, double totalCost) {
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.totalCost = totalCost;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
