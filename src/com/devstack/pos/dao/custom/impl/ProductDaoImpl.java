package com.devstack.pos.dao.custom.impl;

import com.devstack.pos.dao.CrudUtil;
import com.devstack.pos.dao.custom.ProductDao;
import com.devstack.pos.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    @Override
    public boolean save(Product product) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO product VALUES (?,?,?,?,?)",
                product.getProductId(), product.getDescription(),
                product.getUnitPrice(), product.getQtyOnHand(),
                product.getQr()!=null?product.getQr():null);
    }

    @Override
    public boolean update(Product product) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Product findById(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<Product> findAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM product");
        List<Product> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(
                    new Product(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getDouble(3),
                            resultSet.getInt(4),
                            resultSet.getBytes(5)
                    )
            );
        }
        return list;
    }

    @Override
    public long fillableCount() throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.execute("SELECT COUNT(*) FROM product WHERE qty_on_hand<=20");
        if(set.next()){
            return set.getLong(1);
        }
        return 0;
    }
}
