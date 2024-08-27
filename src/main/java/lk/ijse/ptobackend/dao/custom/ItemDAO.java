package lk.ijse.ptobackend.dao.custom;

import lk.ijse.ptobackend.dao.CrudDAO;
import lk.ijse.ptobackend.entity.Item;

import java.sql.Connection;
import java.sql.SQLException;

public interface ItemDAO extends CrudDAO<Item> {
    boolean updateQty(Item item, Connection connection) throws SQLException;
    boolean updateQtyDeleted(String itemID, String orderQty, Connection connection) throws SQLException;
    boolean updateQtyOnHand(String itemID, String qtyOnHand, Connection connection) throws SQLException;
}
