package com.devstack.pos.bo.custom.impl;

import com.devstack.pos.bo.custom.OrderDetailBO;
import com.devstack.pos.dao.DaoFactory;
import com.devstack.pos.dao.custom.OrderDetailsDao;
import com.devstack.pos.dao.custom.ProductDao;
import com.devstack.pos.dto.response.ResponseOrderDetailsDTO;
import com.devstack.pos.entity.OrderDetail;
import com.devstack.pos.entity.Product;
import com.devstack.pos.util.DaoType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailBOImpl implements OrderDetailBO {

    private OrderDetailsDao  orderDetailsDao = DaoFactory.getInstance().getDao(DaoType.ORDER_DETAIL);
    private ProductDao productDao = DaoFactory.getInstance().getDao(DaoType.PRODUCT);

    @Override
    public List<ResponseOrderDetailsDTO> getOrderDetailByOrderId(Integer orderId) throws SQLException, ClassNotFoundException {
        List<OrderDetail> orderDetailsByOrderId = orderDetailsDao.findOrderDetailsByOrderId(orderId);
        List<ResponseOrderDetailsDTO> responseOrderDetailsDTOs = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetailsByOrderId) {
            String productName="UNKNOWN";
            Product selectedProduct = productDao.findById(orderDetail.getProductId());
            if (selectedProduct != null) {
                productName=selectedProduct.getDescription();
            }
            responseOrderDetailsDTOs.add(
                    new ResponseOrderDetailsDTO(
                            orderId,orderDetail.getProductId(),
                            productName,orderDetail.getOrderId(),
                            orderDetail.getUnitPrice(),
                            orderDetail.getUnitPrice()*orderDetail.getQty())
            );
        }
        return responseOrderDetailsDTOs;
    }
}
