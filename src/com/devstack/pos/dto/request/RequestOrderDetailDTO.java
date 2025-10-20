package com.devstack.pos.dto.request;

public class RequestOrderDetailDTO {
    private String productId;
    private double unitPrice;
    private int qty;

    public RequestOrderDetailDTO() {
    }

    public RequestOrderDetailDTO(String productId, double unitPrice, int qty) {
        this.productId = productId;
        this.unitPrice = unitPrice;
        this.qty = qty;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
