package com.devstack.pos.dao.custom.impl;

import com.devstack.pos.dao.CrudUtil;
import com.devstack.pos.dao.custom.OrderDetailsDao;
import com.devstack.pos.entity.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsDaoImpl implements OrderDetailsDao {

    @Override
    public boolean createData(OrderDetail detail) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO order_details VALUES (?,?,?,?,?)",
                detail.getOrderId(),detail.getProductId(), detail.getUnitPrice(), detail.getQty(), detail.getDate());
    }

    @Override
    public List<OrderDetail> findOrderDetailsByOrderId(int orderId) throws SQLException, ClassNotFoundException {
        List<OrderDetail> orderDetails = new ArrayList<>();
       ResultSet set = CrudUtil.execute("SELECT * FROM order_details WHERE order_id = ?",orderId);
       while (set.next()) {
           orderDetails.add(
                   new OrderDetail(
                           set.getInt(1),
                           set.getString(2),
                           set.getDouble(3),
                           set.getInt(4),set.getDate(5)
                   )
           );
       }
       return orderDetails;
    }
}
