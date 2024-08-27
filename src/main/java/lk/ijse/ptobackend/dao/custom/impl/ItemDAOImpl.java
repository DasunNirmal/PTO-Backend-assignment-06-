package lk.ijse.ptobackend.dao.custom.impl;

import lk.ijse.ptobackend.dao.custom.ItemDAO;
import lk.ijse.ptobackend.entity.Item;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {

    static String SAVE_ITEMS = "INSERT INTO Items VALUES (?,?,?,?)";
    static String GET_ALL_ITEMS = "SELECT * FROM Items";
    static String DELETE_ITEMS = "DELETE FROM Items WHERE itemID = ?";
    static String UPDATE_ITEMS = "UPDATE Items SET itemName = ?, itemPrice = ?, itemQty = ? WHERE itemID = ?";
    static String UPDATE_QTY = "UPDATE Items SET itemQty = itemQty - ? WHERE itemID = ?";
    static String UPDATE_QTY_DELETED = "UPDATE Items SET itemQty = itemQty + ? WHERE itemID = ?";
    static String UPDATE_QTY_ON_HAND = "UPDATE Items SET itemQty = ? WHERE itemID = ?";
    static String SEARCH_ITEMS = "SELECT * FROM Items WHERE itemID = ?";

    @Override
    public boolean save(Item item, Connection connection) throws SQLException {
        try {
            var ps = connection.prepareStatement(SAVE_ITEMS);
            ps.setString(1, item.getItemID());
            ps.setString(2, item.getItemName());
            ps.setDouble(3, item.getItemPrice());
            ps.setInt(4, item.getItemQty());

            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public List<Item> getAll(Connection connection) throws SQLException {
        var ps = connection.prepareStatement(GET_ALL_ITEMS);
        var resultSet = ps.executeQuery();
        List<Item> itemsList = new ArrayList<>();
        while (resultSet.next()) {
            Item item = new Item(
                    resultSet.getString("itemID"),
                    resultSet.getString("itemName"),
                    resultSet.getDouble("itemPrice"),
                    resultSet.getInt("itemQty")
            );
            itemsList.add(item);
        }
        return itemsList;
    }

    @Override
    public boolean update(String id, Item item, Connection connection) throws SQLException {
        try {
            var ps = connection.prepareStatement(UPDATE_ITEMS);
            ps.setString(1, item.getItemName());
            ps.setDouble(2, item.getItemPrice());
            ps.setInt(3, item.getItemQty());
            ps.setString(4, item.getItemID());
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public boolean delete(String id, Connection connection) throws SQLException {
        try {
            var ps = connection.prepareStatement(DELETE_ITEMS);
            ps.setString(1, id);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Item search(String id, Connection connection) throws SQLException {
        Item item = null;
        var ps = connection.prepareStatement(SEARCH_ITEMS);
        ps.setString(1, id);
        var resultSet = ps.executeQuery();
        while (resultSet.next()) {
            String itemID = resultSet.getString("itemID");
            String itemName = resultSet.getString("itemName");
            double itemPrice = resultSet.getDouble("itemPrice");
            int itemQty = resultSet.getInt("itemQty");

            item = new Item(itemID, itemName, itemPrice, itemQty);
        }
        return item;
    }

    @Override
    public boolean updateQty(Item item, Connection connection) throws SQLException {
        try {
            var ps = connection.prepareStatement(UPDATE_QTY);

            ps.setInt(1, item.getItemQty());
            ps.setString(2, item.getItemID());
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public boolean updateQtyDeleted(String itemID, String orderQty, Connection connection) throws SQLException {
        try {
            var ps = connection.prepareStatement(UPDATE_QTY_DELETED);

            ps.setInt(1, Integer.parseInt(orderQty));
            ps.setString(2, itemID);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public boolean updateQtyOnHand(String itemID, String qtyOnHand, Connection connection) throws SQLException {
        try {
            var ps = connection.prepareStatement(UPDATE_QTY_ON_HAND);

            ps.setInt(1, Integer.parseInt(qtyOnHand));
            ps.setString(2, itemID);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
