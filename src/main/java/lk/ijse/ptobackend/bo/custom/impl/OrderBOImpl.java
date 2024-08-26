package lk.ijse.ptobackend.bo.custom.impl;

import lk.ijse.ptobackend.bo.custom.OrderBO;
import lk.ijse.ptobackend.dao.DAOFactory;
import lk.ijse.ptobackend.dao.custom.ItemDAO;
import lk.ijse.ptobackend.dao.custom.OrderDAO;
import lk.ijse.ptobackend.dao.custom.OrderDetailDAO;
import lk.ijse.ptobackend.dto.CombinedOrderDTO;
import lk.ijse.ptobackend.dto.ItemDTO;
import lk.ijse.ptobackend.dto.OrderDTO;
import lk.ijse.ptobackend.dto.OrderDetailsDTO;
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
                boolean isUpdated = itemDAO.updateQty(new Item(combinedOrderDTO.getItemID(),combinedOrderDTO.getItemName(),combinedOrderDTO.getItemPrice(),combinedOrderDTO.getItemQty()),connection);
                if(isUpdated){
                    boolean isOrderDetailsSaved = orderDetailDAO.save(new OrderDetails(combinedOrderDTO.getOrderID(), combinedOrderDTO.getItemID(), combinedOrderDTO.getItemName(),
                            combinedOrderDTO.getItemPrice(), combinedOrderDTO.getItemQty(), combinedOrderDTO.getOrderQty(), combinedOrderDTO.getTotalPrice()),connection);
                    if(!isOrderDetailsSaved){
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

}
