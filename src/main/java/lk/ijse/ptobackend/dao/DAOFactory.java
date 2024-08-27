package lk.ijse.ptobackend.dao;

import lk.ijse.ptobackend.dao.custom.impl.*;

public class DAOFactory {

    private static DAOFactory daoFactory;
    private DAOFactory() {}

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        CUSTOMERS,ITEMS,ORDERS,ORDER_DETAILS,COMBINED_ORDER
    }

    public SuperDAO getDAO(DAOTypes boType) {
        switch (boType) {
            case CUSTOMERS:
                return new CustomerDAOImpl();
            case ITEMS:
                return new ItemDAOImpl();
            case ORDERS:
                return new OrderDAOImpl();
                case ORDER_DETAILS:
                    return new OrderDetailDAOImpl();
                    case COMBINED_ORDER:
                        return new CombinedOrderDAOImpl();
                default: return null;
        }
    }
}
