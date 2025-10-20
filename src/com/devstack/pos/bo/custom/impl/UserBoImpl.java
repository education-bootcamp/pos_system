package com.devstack.pos.bo.custom.impl;

import com.devstack.pos.bo.custom.UserBo;
import com.devstack.pos.dao.DaoFactory;
import com.devstack.pos.dao.custom.UserDao;
import com.devstack.pos.dto.request.RequestUserDTO;
import com.devstack.pos.dto.response.ResponseUserDTO;
import com.devstack.pos.entity.User;
import com.devstack.pos.util.DaoType;
import com.devstack.pos.util.PasswordHash;

import java.sql.SQLException;
import java.util.UUID;

public class UserBoImpl implements UserBo {

   // private UserDao userDao = (UserDao) DaoFactory.getInstance().getDao(DaoType.USER);
    private UserDao userDao = DaoFactory.getInstance().getDao(DaoType.USER);

    @Override
    public boolean registerUser(RequestUserDTO userDTO) throws SQLException, ClassNotFoundException {
        return userDao.save(new User(
                UUID.randomUUID().toString(),
                userDTO.getEmail(),
                userDTO.getDisplayName(),
                userDTO.getContactNumber(),
                PasswordHash.hashPassword(userDTO.getPassword())
        ));
    }

    @Override
    public ResponseUserDTO loginUser(String email, String password) throws SQLException, ClassNotFoundException {
        User selectedUser = userDao.findByUserEmail(email);
        if(selectedUser == null){
            return new ResponseUserDTO(null,null,404,false,"User Not Found");
        }

        boolean passwordCheckedStatus = PasswordHash.checkPassword(password, selectedUser.getPassword());

        if(passwordCheckedStatus){
            return new ResponseUserDTO(selectedUser.getDisplayName(),selectedUser.getContactNumber(),200,true,"Login Successful");
        }else{
            return new ResponseUserDTO(null,null,401,false,"wrong password");
        }
    }
}
