package com.devstack.pos.dao.custom.impl;

import com.devstack.pos.dao.CrudUtil;
import com.devstack.pos.dao.custom.OrderDao;
import com.devstack.pos.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    @Override
    public int findLastOrderId() throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.execute("SELECT * FROM orders ORDER BY order_id DESC LIMIT 1");
        if (set.next()) {
            return set.getInt("order_id");
        }
        return 0;
    }

    @Override
    public boolean save(Order order) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Order order) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Integer integer) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Order findById(Integer integer) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<Order> findAll() throws SQLException, ClassNotFoundException {
        return List.of();
    }
}
