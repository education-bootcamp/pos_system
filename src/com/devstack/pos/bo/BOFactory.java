package com.devstack.pos.bo;

import com.devstack.pos.bo.custom.impl.CustomerBOImpl;
import com.devstack.pos.bo.custom.impl.OrderBOImpl;
import com.devstack.pos.bo.custom.impl.ProductBOImpl;
import com.devstack.pos.bo.custom.impl.UserBoImpl;
import com.devstack.pos.dao.custom.impl.UserDaoImpl;
import com.devstack.pos.util.BoType;

import static com.devstack.pos.util.DaoType.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getInstance() {
        if (boFactory == null) {
            boFactory = new BOFactory();
        }
        return boFactory;
    }

    public <T> T getBo(BoType boType) {
        switch (boType) {
            case USER:
                return (T) new UserBoImpl();
            case CUSTOMER:
                return (T) new CustomerBOImpl();
            case PRODUCT:
                return (T) new ProductBOImpl();
            case ORDER:
                return (T) new OrderBOImpl();
            default:
                return null;
        }
    }
}
