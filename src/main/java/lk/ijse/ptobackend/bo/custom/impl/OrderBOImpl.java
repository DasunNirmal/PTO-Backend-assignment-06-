package lk.ijse.ptobackend.bo.custom.impl;

import lk.ijse.ptobackend.bo.custom.OrderBO;
import lk.ijse.ptobackend.dao.DAOFactory;
import lk.ijse.ptobackend.dao.custom.ItemDAO;
import lk.ijse.ptobackend.dao.custom.OrderDAO;
import lk.ijse.ptobackend.dao.custom.OrderDetailDAO;
import lk.ijse.ptobackend.dto.CombinedOrderDTO;
import lk.ijse.ptobackend.entity.Item;
import lk.ijse.ptobackend.entity.Order;
import lk.ijse.ptobackend.entity.OrderDetails;

import java.sql.Connection;
import java.sql.SQLException;

public class OrderBOImpl implements OrderBO {

    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERS);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEMS);
    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);

    @Override
    public boolean saveOrder(CombinedOrderDTO combinedOrderDTO, Connection connection) throws SQLException {

        try {
            connection.setAutoCommit(false);

            boolean isOrderSaved = orderDAO.save(new Order(combinedOrderDTO.getOrderID(),combinedOrderDTO.getOrderDate(),combinedOrderDTO.getCustomerID()),connection);
            if(isOrderSaved){
                boolean isOrderDetailsSaved = orderDetailDAO.save(new OrderDetails(combinedOrderDTO.getOrderID(), combinedOrderDTO.getItemID(), combinedOrderDTO.getItemName(),
                        combinedOrderDTO.getItemPrice(), combinedOrderDTO.getItemQty(), combinedOrderDTO.getOrderQty(), combinedOrderDTO.getTotalPrice()),connection);
                if(isOrderDetailsSaved){
                    boolean isUpdated = itemDAO.updateQty(new Item(combinedOrderDTO.getItemID(),combinedOrderDTO.getItemName(),combinedOrderDTO.getItemPrice(),combinedOrderDTO.getOrderQty()),connection);
                    if(!isUpdated){
                        return false;
                    }
                } else {
                    return false;
                }
            }
            connection.commit();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteOrder(String orderID, String itemID, String orderQty, Connection connection) throws SQLException {
        boolean isDeleted = false;
        boolean isUpdated = false;
        try {
            connection.setAutoCommit(false); // Start transaction

            // Attempt to delete the order
            isDeleted = orderDAO.delete(orderID, connection);
            if (isDeleted) {
                // Attempt to update the item quantity
                isUpdated = itemDAO.updateQtyDeleted(itemID, orderQty, connection);
                if (isUpdated) {
                    connection.commit(); // Commit transaction if both operations succeed
                    return true;
                } else {
                    connection.rollback(); // Rollback transaction if updating quantity fails
                    return false;
                }
            } else {
                connection.rollback(); // Rollback transaction if deleting the order fails
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete order", e);
        }
    }

}
