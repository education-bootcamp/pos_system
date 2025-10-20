package com.devstack.pos.dao.custom;

import com.devstack.pos.dao.CrudDao;
import com.devstack.pos.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao extends CrudDao<User, String> {
    public List<User> searchByName(String name);
    public User findByUserEmail(String email) throws SQLException, ClassNotFoundException;
}
