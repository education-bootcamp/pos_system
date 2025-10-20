package com.devstack.pos.dao.custom.impl;

import com.devstack.pos.dao.CrudUtil;
import com.devstack.pos.dao.custom.CustomerDao;
import com.devstack.pos.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public List<Customer> searchAll(String searchText) throws SQLException, ClassNotFoundException {
        searchText = "%" + searchText + "%";
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM customer WHERE name LIKE ? OR address LIKE ?", searchText, searchText);
        List<Customer> customers = new ArrayList<>();
        while (resultSet.next()) {
            customers.add(toCustomer(resultSet));
        }
        return customers;
    }

    @Override
    public boolean save(Customer customer) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO customer VALUES(?,?,?,?)", customer.getId(), customer.getName(), customer.getSalary(), customer.getAddress());
    }

    @Override
    public boolean update(Customer customer) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE customer SET name=?, address=?, salary=? WHERE customer_id=?",
                 customer.getName(), customer.getAddress(),customer.getSalary(),customer.getId());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM customer WHERE customer_id=?", s);
    }

    @Override
    public Customer findById(String s) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM customer WHERE customer_id=?", s);
        if (resultSet.next()) {
            return toCustomer(resultSet);
        }
        return null;
    }

    @Override
    public List<Customer> findAll() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = CrudUtil.execute("SELECT * FROM customer");
        List<Customer> customers = new ArrayList<>();
        while (resultSet.next()) {
            customers.add(toCustomer(resultSet));
        }
        return customers;
    }

    private Customer toCustomer(ResultSet rs) throws SQLException, ClassNotFoundException {
        if (rs==null){
            return null;
        }
        return new Customer(
                rs.getString(1),
                rs.getString(2),
                rs.getString(4),
                rs.getDouble(3)
        );
    }

}
