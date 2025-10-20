package com.devstack.pos.view.tm;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;

public class ProductTM {
    private long id;
    private String description;
    private double unitPrice;
    private long qtyOnHand;
    private Button qrAv;
    private ButtonBar tools;

    public ProductTM() {
    }

    public ProductTM(long id, String description, double unitPrice, long qtyOnHand, Button qrAv, ButtonBar tools) {
        this.id = id;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
        this.qrAv = qrAv;
        this.tools = tools;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(long qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public Button getQrAv() {
        return qrAv;
    }

    public void setQrAv(Button qrAv) {
        this.qrAv = qrAv;
    }

    public ButtonBar getTools() {
        return tools;
    }

    public void setTools(ButtonBar tools) {
        this.tools = tools;
    }
}
