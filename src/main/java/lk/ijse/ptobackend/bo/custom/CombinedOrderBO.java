package lk.ijse.ptobackend.bo.custom;

import lk.ijse.ptobackend.bo.SuperBO;
import lk.ijse.ptobackend.dto.CombinedOrderDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CombinedOrderBO extends SuperBO {
    List<CombinedOrderDTO> getAllOrders(Connection connection) throws SQLException;
}
