package com.devstack.pos.dao;

import com.devstack.pos.dao.custom.UserDao;
import com.devstack.pos.dao.custom.impl.*;
import com.devstack.pos.util.DaoType;

public class DaoFactory {
    private static DaoFactory daoFactory;

    private DaoFactory() {
    }

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            daoFactory = new DaoFactory();
        }
        return daoFactory;
    }

    /*public SuperDao getDao(DaoType daoType) {
        switch (daoType) {
            case USER:
                return new UserDaoImpl();
            case CUSTOMER:
                return null;
            case PRODUCT:
                return null;
            case ORDER:
                return null;
            default:
                return null;
        }
    }*/
    public <T> T getDao(DaoType daoType) {
        switch (daoType) {
            case USER:
                return (T) new UserDaoImpl();
            case CUSTOMER:
                return (T) new CustomerDaoImpl();
            case PRODUCT:
                return (T) new ProductDaoImpl();
            case ORDER:
                return (T) new OrderDaoImpl();
            case ORDER_DETAIL:
                return (T) new OrderDetailsDaoImpl();
            default:
                return null;
        }
    }
}
