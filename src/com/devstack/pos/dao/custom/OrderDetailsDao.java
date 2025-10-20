package com.devstack.pos.dao.custom;

import com.devstack.pos.entity.OrderDetail;

import java.sql.SQLException;

public interface OrderDetailsDao {
    public boolean createData(OrderDetail detail) throws SQLException, ClassNotFoundException;
}
