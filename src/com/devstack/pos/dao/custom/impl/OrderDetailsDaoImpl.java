package com.devstack.pos.dao.custom.impl;

import com.devstack.pos.dao.CrudUtil;
import com.devstack.pos.dao.custom.OrderDetailsDao;
import com.devstack.pos.entity.OrderDetail;

import java.sql.SQLException;

public class OrderDetailsDaoImpl implements OrderDetailsDao {

    @Override
    public boolean createData(OrderDetail detail) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO order_details VALUES (?,?,?,?,?)",
                detail.getOrderId(),detail.getProductId(), detail.getUnitPrice(), detail.getQty(), detail.getDate());
    }
}
