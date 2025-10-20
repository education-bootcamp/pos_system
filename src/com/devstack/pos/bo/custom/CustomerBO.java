package com.devstack.pos.bo.custom;

import com.devstack.pos.dto.request.RequestCustomerDTO;
import com.devstack.pos.dto.response.ResponseCustomerDTO;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBO {
    public boolean saveCustomer(RequestCustomerDTO dto) throws SQLException, ClassNotFoundException;
    public boolean updateCustomer(RequestCustomerDTO dto, String id) throws SQLException, ClassNotFoundException;
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;
    public ResponseCustomerDTO getCustomerById(String id) throws SQLException, ClassNotFoundException;
    public List<ResponseCustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException;
    public List<ResponseCustomerDTO> searchCustomers(String searchText) throws SQLException, ClassNotFoundException;
    public List<String> loadAllIds() throws SQLException, ClassNotFoundException;
}
