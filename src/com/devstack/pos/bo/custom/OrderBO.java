package com.devstack.pos.bo.custom;

import com.devstack.pos.dto.request.RequestOrderDTO;

import java.sql.SQLException;

public interface OrderBO {
    public int findLastOrderId() throws SQLException, ClassNotFoundException;
    public boolean createOrder(RequestOrderDTO dto) throws SQLException, ClassNotFoundException;
}
