package com.devstack.pos.dao.custom.impl;


import com.devstack.pos.dao.custom.UserDao;
import com.devstack.pos.entity.User;
import com.devstack.pos.util.PasswordHash;

import java.sql.SQLException;
import java.util.UUID;

class UserDaoImplTest {


    void findByUserEmail() {
    }


    void save() throws SQLException, ClassNotFoundException {
        UserDao dao = new  UserDaoImpl();
        User user = new User(
                UUID.randomUUID().toString(),
                "xyz@gmail.com",
                "Kamal",
                "038",
                PasswordHash.hashPassword("1324")
        );
        boolean isSaved = dao.save(user);
        System.out.println(isSaved);
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDaoImplTest test = new UserDaoImplTest();
        test.save();
    }

}