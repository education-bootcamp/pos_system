package com.devstack.pos.dto.request;

import java.util.Date;
import java.util.List;

public class RequestOrderDTO {
    private int orderId;
    private String customerId;
    private double totalCost;
    private Date date;
    private List<RequestOrderDetailDTO> orderDetailDTOList;

    public RequestOrderDTO() {
    }

    public RequestOrderDTO(int orderId, String customerId, double totalCost, Date date, List<RequestOrderDetailDTO> orderDetailDTOList) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.totalCost = totalCost;
        this.date = date;
        this.orderDetailDTOList = orderDetailDTOList;
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

    public List<RequestOrderDetailDTO> getOrderDetailDTOList() {
        return orderDetailDTOList;
    }

    public void setOrderDetailDTOList(List<RequestOrderDetailDTO> orderDetailDTOList) {
        this.orderDetailDTOList = orderDetailDTOList;
    }
}
