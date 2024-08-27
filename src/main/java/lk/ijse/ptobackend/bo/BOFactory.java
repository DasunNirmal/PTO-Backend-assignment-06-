package lk.ijse.ptobackend.bo;

import lk.ijse.ptobackend.bo.custom.impl.*;

public class BOFactory {

    private static BOFactory boFactory;
    private BOFactory() {}

    public static BOFactory getBoFactory() {
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes {
        CUSTOMERS,ITEMS,ORDERS,ORDER_DETAILS,COMBINED_ORDER
    }

    public SuperBO getBO(BOTypes boType) {
        switch (boType) {
            case CUSTOMERS:
                return new CustomerBOImpl();
            case ITEMS:
                return new ItemBOImpl();
                case ORDERS:
                    return new OrderBOImpl();
                    case ORDER_DETAILS:
                        return new OrderDetailBOImpl();
                        case COMBINED_ORDER:
                            return new CombinedOrderBOImpl();
                default: return null;
        }
    }
}
