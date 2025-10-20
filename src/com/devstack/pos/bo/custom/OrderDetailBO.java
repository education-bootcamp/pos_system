package com.devstack.pos.bo.custom;

import com.devstack.pos.dto.response.ResponseOrderDetailsDTO;
import com.devstack.pos.entity.OrderDetail;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailBO {
    public List<ResponseOrderDetailsDTO> getOrderDetailByOrderId(Integer orderId) throws SQLException, ClassNotFoundException;
}
