package com.devstack.pos.view.tm;

import javafx.scene.control.ButtonBar;

public class CustomerTm {
    private long id;
    private String name;
    private String address;
    private double salary;
    private ButtonBar tools;

    public CustomerTm() {
    }

    public CustomerTm(long id, String name, String address, double salary, ButtonBar tools) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.salary = salary;
        this.tools = tools;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public ButtonBar getTools() {
        return tools;
    }

    public void setTools(ButtonBar tools) {
        this.tools = tools;
    }
}
