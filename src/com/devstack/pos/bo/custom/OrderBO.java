package com.devstack.pos.bo.custom;

import java.sql.SQLException;

public interface OrderBO {
    public int findLastOrderId() throws SQLException, ClassNotFoundException;
}
