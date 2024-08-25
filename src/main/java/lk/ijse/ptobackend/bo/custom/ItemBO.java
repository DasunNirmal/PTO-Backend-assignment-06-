package lk.ijse.ptobackend.bo.custom;

import lk.ijse.ptobackend.bo.SuperBO;
import lk.ijse.ptobackend.dto.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ItemBO extends SuperBO {
    boolean saveItem(ItemDTO itemDTO, Connection connection) throws SQLException;

    List<ItemDTO> getAllItems(Connection connection) throws SQLException;

    boolean updateItem(String customerID, ItemDTO itemDTO, Connection connection) throws SQLException;

    boolean deleteItem(String itemID, Connection connection) throws SQLException;

    ItemDTO searchItem(String itemID, Connection connection) throws SQLException;
}
