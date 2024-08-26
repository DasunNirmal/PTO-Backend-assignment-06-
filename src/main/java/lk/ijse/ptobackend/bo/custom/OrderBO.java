package lk.ijse.ptobackend.bo.custom;

import lk.ijse.ptobackend.bo.SuperBO;
import lk.ijse.ptobackend.dto.CombinedOrderDTO;

import java.sql.Connection;
import java.sql.SQLException;

public interface OrderBO extends SuperBO {
    boolean saveOrder(CombinedOrderDTO combinedOrderDTO, Connection connection) throws SQLException;
}
