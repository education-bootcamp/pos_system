package com.devstack.pos.bo.custom.impl;

import com.devstack.pos.bo.custom.OrderBO;
import com.devstack.pos.dao.DaoFactory;
import com.devstack.pos.dao.custom.CustomerDao;
import com.devstack.pos.dao.custom.OrderDao;
import com.devstack.pos.dao.custom.OrderDetailsDao;
import com.devstack.pos.dao.custom.ProductDao;
import com.devstack.pos.dao.custom.impl.OrderDaoImpl;
import com.devstack.pos.db.DbConnection;
import com.devstack.pos.dto.request.RequestOrderDTO;
import com.devstack.pos.dto.request.RequestOrderDetailDTO;
import com.devstack.pos.dto.response.ResponseOrderDTO;
import com.devstack.pos.entity.Customer;
import com.devstack.pos.entity.Order;
import com.devstack.pos.entity.OrderDetail;
import com.devstack.pos.util.DaoType;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderBOImpl implements OrderBO {
    private final OrderDao orderDao = DaoFactory.getInstance().getDao(DaoType.ORDER);
    private final CustomerDao customerDao = DaoFactory.getInstance().getDao(DaoType.CUSTOMER);
    private final ProductDao productDao = DaoFactory.getInstance().getDao(DaoType.PRODUCT);
    private final OrderDetailsDao orderDetailsDao = DaoFactory.getInstance().getDao(DaoType.ORDER_DETAIL);
    @Override
    public int findLastOrderId() throws SQLException, ClassNotFoundException {
        return orderDao.findLastOrderId();
    }

    @Override
    public boolean createOrder(RequestOrderDTO dto) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        con.setAutoCommit(false);
        boolean order = orderDao.createOrder(dto);
        if (order) {
            for(RequestOrderDetailDTO d: dto.getOrderDetailDTOList()){
                boolean data = orderDetailsDao.createData(
                        new OrderDetail(dto.getOrderId(),
                                d.getProductId(), d.getUnitPrice(), d.getQty(), dto.getDate())
                );
                if(data){
                    boolean isUpdated = productDao.updateQty(d.getProductId(), d.getQty());
                    if (!isUpdated){
                        con.rollback();
                        return false;
                    }
                }else{
                    con.rollback();
                    return false;
                }
            }
        }else{
            con.rollback();
        }
        con.commit();
        return true;
    }

    @Override
    public List<ResponseOrderDTO> getOrderHistory(LocalDate date) throws SQLException, ClassNotFoundException {
        List<Order> orderHistory = orderDao.getOrderHistory(date);
        List<ResponseOrderDTO> dtoList = new ArrayList<ResponseOrderDTO>();
        for(Order o: orderHistory){
            String customerName="UNKNOWN";
            Customer selectedCustomer = customerDao.findById(o.getCustomerId());
            if (selectedCustomer != null) {
                customerName = selectedCustomer.getName();
            }
            dtoList.add(new ResponseOrderDTO(
                    o.getOrderId(),customerName,o.getTotalCost(),o.getDate()
            ));
        }
        return dtoList;
    }

    @Override
    public ResponseOrderDTO findOrderById(int orderId) throws SQLException, ClassNotFoundException {
        Order order = orderDao.findById(orderId);
        if(order != null){
            return new ResponseOrderDTO(
                    order.getOrderId(),
                    order.getCustomerId(),
                    order.getTotalCost(),
                    order.getDate()
            );
        }
        return null;
    }
}
