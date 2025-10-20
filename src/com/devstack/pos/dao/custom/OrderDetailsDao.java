package com.devstack.pos.dao.custom;

import com.devstack.pos.entity.OrderDetail;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailsDao {
    public boolean createData(OrderDetail detail) throws SQLException, ClassNotFoundException;
    public List<OrderDetail> findOrderDetailsByOrderId(int orderId) throws SQLException, ClassNotFoundException;
}
