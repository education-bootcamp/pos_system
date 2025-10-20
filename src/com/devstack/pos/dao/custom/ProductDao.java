package com.devstack.pos.dao.custom;

import com.devstack.pos.dao.CrudDao;
import com.devstack.pos.entity.Product;

import java.sql.SQLException;

public interface ProductDao extends CrudDao<Product, String> {
    public long fillableCount() throws SQLException, ClassNotFoundException;
}
