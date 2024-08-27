package lk.ijse.ptobackend.bo.custom;

import lk.ijse.ptobackend.bo.SuperBO;
import lk.ijse.ptobackend.dto.CombinedOrderDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderBO extends SuperBO {
    boolean saveOrder(CombinedOrderDTO combinedOrderDTO, Connection connection) throws SQLException;

    boolean deleteOrder(String orderID, String itemID, String orderQty, Connection connection) throws SQLException;
}
