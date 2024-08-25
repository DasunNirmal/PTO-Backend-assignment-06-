package lk.ijse.ptobackend.bo.custom;

import lk.ijse.ptobackend.bo.SuperBO;
import lk.ijse.ptobackend.dto.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ItemBO extends SuperBO {
    boolean saveItem(ItemDTO itemDTO, Connection connection) throws SQLException;

    List<ItemDTO> getAllCustomers(Connection connection) throws SQLException;
}
