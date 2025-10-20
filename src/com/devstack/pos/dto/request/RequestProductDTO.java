package com.devstack.pos.dto.request;

public class RequestProductDTO {
    private String description;
    private double unitPrice;
    private int qtyOnHand;

    public RequestProductDTO() {
    }

    public RequestProductDTO(String description, double unitPrice, int qtyOnHand) {
        this.description = description;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }
}
