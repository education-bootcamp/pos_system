package com.devstack.pos.bo.custom.impl;

import com.devstack.pos.bo.custom.OrderBO;
import com.devstack.pos.dao.DaoFactory;
import com.devstack.pos.dao.custom.OrderDao;
import com.devstack.pos.dao.custom.impl.OrderDaoImpl;
import com.devstack.pos.util.DaoType;

import java.sql.SQLException;

public class OrderBOImpl implements OrderBO {
    private final OrderDao orderDao = DaoFactory.getInstance().getDao(DaoType.ORDER);
    @Override
    public int findLastOrderId() throws SQLException, ClassNotFoundException {
        return orderDao.findLastOrderId();
    }
}
