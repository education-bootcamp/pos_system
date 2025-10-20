package com.devstack.pos.bo.custom;

import com.devstack.pos.dto.request.RequestOrderDTO;
import com.devstack.pos.dto.response.ResponseOrderDTO;
import com.devstack.pos.dto.response.StatisticsResponseDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface OrderBO {
    public int findLastOrderId() throws SQLException, ClassNotFoundException;
    public boolean createOrder(RequestOrderDTO dto) throws SQLException, ClassNotFoundException;
    public List<ResponseOrderDTO> getOrderHistory(LocalDate date) throws SQLException, ClassNotFoundException;
    public ResponseOrderDTO findOrderById(int orderId) throws SQLException, ClassNotFoundException;
    public List<StatisticsResponseDTO> loadStatistics(LocalDate from, LocalDate to) throws SQLException, ClassNotFoundException;
}
