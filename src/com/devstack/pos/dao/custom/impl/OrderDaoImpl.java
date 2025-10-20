package com.devstack.pos.dao.custom.impl;

import com.devstack.pos.dao.CrudUtil;
import com.devstack.pos.dao.custom.OrderDao;
import com.devstack.pos.dto.request.RequestOrderDTO;
import com.devstack.pos.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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
    public boolean createOrder(RequestOrderDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO orders VALUES (?,?,?,?)",
                dto.getOrderId(), dto.getCustomerId(), dto.getTotalCost(), dto.getDate());
    }

    @Override
    public List<Order> getOrderHistory(LocalDate date) throws SQLException, ClassNotFoundException {
        System.out.println(date);
        ResultSet set = CrudUtil.execute(
                "SELECT * FROM orders WHERE DATE(date) = ? ORDER BY date DESC",
                date
        );
        List<Order> list = new ArrayList<>();
        while (set.next()){
            list.add(
                    new Order(
                            set.getInt(1),
                            set.getString(2),set.getDouble(3),set.getDate(4)
                    )
            );
        }
        return list;
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
        ResultSet set = CrudUtil.execute("SELECT * FROM orders WHERE order_id = ?", integer);
        if (set.next()) {
            return new Order(
                    set.getInt(1),
                    set.getString(2),set.getDouble(3),set.getDate(4)
            );
        }
        return null;
    }

    @Override
    public List<Order> findAll() throws SQLException, ClassNotFoundException {
        return List.of();
    }
}
