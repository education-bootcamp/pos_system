package com.devstack.pos.dto.response;

public class ResponseProductDTO {
    private String productId;
    private String description;
    private double unitPrice;
    private int qtyOnHand;
    private byte[] qr;

    public ResponseProductDTO() {
    }

    public ResponseProductDTO(String productId, String description, double unitPrice, int qtyOnHand, byte[] qr) {
        this.productId = productId;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
        this.qr = qr;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public byte[] getQr() {
        return qr;
    }

    public void setQr(byte[] qr) {
        this.qr = qr;
    }
}
