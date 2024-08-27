package lk.ijse.ptobackend.bo.custom.impl;

import lk.ijse.ptobackend.bo.custom.CombinedOrderBO;
import lk.ijse.ptobackend.dao.DAOFactory;
import lk.ijse.ptobackend.dao.custom.CombinedOrderDAO;
import lk.ijse.ptobackend.dao.custom.ItemDAO;
import lk.ijse.ptobackend.dto.CombinedOrderDTO;
import lk.ijse.ptobackend.entity.CombinedOrder;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CombinedOrderBOImpl implements CombinedOrderBO {

    CombinedOrderDAO combinedOrderDAO = (CombinedOrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.COMBINED_ORDER);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEMS);

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

    @Override
    public CombinedOrderDTO searchOrdersByID(String orderID, Connection connection) throws SQLException {
        CombinedOrder cO = combinedOrderDAO.search(orderID, connection);
        if (cO != null) {
            return new CombinedOrderDTO(cO.getOrderID(),cO.getOrderDate(),cO.getCustomerID(),cO.getItemID(),
                    cO.getItemName(),cO.getItemPrice(),cO.getItemQty(),cO.getOrderQty(),cO.getTotalPrice());
        } else {
            return null;
        }
    }

    @Override
    public boolean updateOrders(String orderID, String itemID, String qtyOnHand, CombinedOrderDTO cO, Connection connection) throws SQLException {
        boolean isSuccess = false;
        try {
            connection.setAutoCommit(false);

            // Update the order details
            boolean isUpdated = combinedOrderDAO.update(orderID, new CombinedOrder(cO.getOrderID(), cO.getOrderDate(), cO.getCustomerID(),
                    cO.getItemID(), cO.getItemName(), cO.getItemPrice(), cO.getItemQty(), cO.getOrderQty(), cO.getTotalPrice()), connection);

            if (isUpdated) {
                // Update the item quantity directly
                int orderQty = Integer.parseInt(qtyOnHand);
                boolean isItemUpdated = itemDAO.updateQtyOnHand(itemID, String.valueOf(orderQty), connection);
                if (isItemUpdated) {
                    connection.commit();
                    isSuccess = true;
                } else {
                    connection.rollback();
                }
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                throw new SQLException("Error rolling back transaction", rollbackEx);
            }
            throw new SQLException("Error updating orders", e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException autoCommitEx) {
                throw new SQLException("Error resetting auto-commit", autoCommitEx);
            }
        }
        return isSuccess;
    }
}
