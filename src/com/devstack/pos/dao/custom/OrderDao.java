package com.devstack.pos.dao.custom;

import com.devstack.pos.dao.CrudDao;
import com.devstack.pos.dto.request.RequestOrderDTO;
import com.devstack.pos.entity.Order;

import java.sql.SQLException;

public interface OrderDao extends CrudDao<Order, Integer> {
    public int findLastOrderId() throws SQLException, ClassNotFoundException;
    public boolean createOrder(RequestOrderDTO dto) throws SQLException, ClassNotFoundException;
}
