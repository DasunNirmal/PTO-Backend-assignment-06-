package lk.ijse.ptobackend.bo.custom.impl;

import lk.ijse.ptobackend.bo.custom.CombinedOrderBO;
import lk.ijse.ptobackend.dao.DAOFactory;
import lk.ijse.ptobackend.dao.custom.CombinedOrderDAO;
import lk.ijse.ptobackend.dto.CombinedOrderDTO;
import lk.ijse.ptobackend.entity.CombinedOrder;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CombinedOrderBOImpl implements CombinedOrderBO {

    CombinedOrderDAO combinedOrderDAO = (CombinedOrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.COMBINED_ORDER);

    @Override
    public List<CombinedOrderDTO> getAllOrders(Connection connection) throws SQLException {
        List<CombinedOrderDTO> combinedOrderDTOS = new ArrayList<>();
        List<CombinedOrder> combinedOrders = combinedOrderDAO.getAll(connection);
        for (CombinedOrder cO : combinedOrders) {
            combinedOrderDTOS.add(new CombinedOrderDTO(cO.getOrderID(),cO.getOrderDate(),cO.getCustomerID(),cO.getItemID(),
                    cO.getItemName(),cO.getItemPrice(),cO.getItemQty(),cO.getOrderQty(),cO.getTotalPrice()));
        }
        return combinedOrderDTOS;
    }
}
