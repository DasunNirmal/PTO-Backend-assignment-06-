package lk.ijse.ptobackend.dao;

import lk.ijse.ptobackend.dao.custom.impl.CustomerDAOImpl;

public class DAOFactory {

    private static DAOFactory daoFactory;
    private DAOFactory() {}

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        CUSTOMERS,ITEMS,ORDERS
    }

    public SuperDAO getDAO(DAOTypes boType) {
        switch (boType) {
            case CUSTOMERS:
                return new CustomerDAOImpl();
                default: return null;
        }
    }
}
