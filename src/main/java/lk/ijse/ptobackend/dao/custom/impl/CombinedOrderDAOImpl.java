package lk.ijse.ptobackend.dao.custom.impl;

import lk.ijse.ptobackend.dao.custom.CombinedOrderDAO;
import lk.ijse.ptobackend.entity.CombinedOrder;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CombinedOrderDAOImpl implements CombinedOrderDAO {

    static String GET_ALL_ORDERS = "SELECT od.orderID,od.itemID,od.itemName,od.itemPrice,od.itemQty,od.orderQty,o.orderDate,o.customerID,od.totalPrice\n" +
            "FROM OrderDetails od\n" +
            "JOIN Orders o ON od.orderID = o.orderID\n" +
            "ORDER BY od.orderID ASC";
    static String SEARCH_ORDERS = "SELECT od.orderID, od.itemID, od.itemName, od.itemPrice, od.itemQty, od.orderQty, o.orderDate, o.customerID, od.totalPrice " +
            "FROM OrderDetails od " +
            "JOIN Orders o ON od.orderID = o.orderID " +
            "WHERE od.orderID = ? " +
            "ORDER BY od.orderID ASC";
    static String UPDATE_ORDERS = "UPDATE OrderDetails SET itemID = ?, itemName = ?, itemPrice = ?, itemQty = ?, orderQty = ?, totalPrice = ? WHERE orderID = ?";

    @Override
    public boolean save(CombinedOrder dto, Connection connection) throws SQLException {
        return false;
    }

    @Override
    public List<CombinedOrder> getAll(Connection connection) throws SQLException {
        var ps = connection.prepareStatement(GET_ALL_ORDERS);
        var rs = ps.executeQuery();
        List<CombinedOrder> combinedOrders = new ArrayList<>();
        while (rs.next()) {
            CombinedOrder combinedOrder = new CombinedOrder(
                    rs.getString("orderID"),
                    rs.getString("orderDate"),
                    rs.getString("customerID"),
                    rs.getString("itemID"),
                    rs.getString("itemName"),
                    rs.getDouble("itemPrice"),
                    rs.getInt("itemQty"),
                    rs.getInt("orderQty"),
                    rs.getInt("totalPrice")
            );
            combinedOrders.add(combinedOrder);
        }
        return combinedOrders;
    }

    @Override
    public boolean update(String id, CombinedOrder dto, Connection connection) throws SQLException {
        try {
            var ps = connection.prepareStatement(UPDATE_ORDERS);
            ps.setString(1, dto.getItemID());
            ps.setString(2, dto.getItemName());
            ps.setDouble(3, dto.getItemPrice());
            ps.setInt(4, dto.getItemQty());
            ps.setInt(5, dto.getOrderQty());
            ps.setDouble(6, dto.getTotalPrice());
            ps.setString(7, id);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public boolean delete(String id, Connection connection) throws SQLException {
        return false;
    }

    @Override
    public CombinedOrder search(String id, Connection connection) throws SQLException {
        CombinedOrder combinedOrder = null;
        var ps = connection.prepareStatement(SEARCH_ORDERS);
        ps.setString(1, id);
        var rs = ps.executeQuery();
        while (rs.next()) {
            String orderID = rs.getString("orderID");
            String orderDate = rs.getString("orderDate");
            String customerID = rs.getString("customerID");
            String itemID = rs.getString("itemID");
            String itemName = rs.getString("itemName");
            double itemPrice = rs.getDouble("itemPrice");
            int itemQty = rs.getInt("itemQty");
            int orderQty = rs.getInt("orderQty");
            int totalPrice = rs.getInt("totalPrice");

            combinedOrder = new CombinedOrder(orderID,orderDate,customerID,itemID,itemName,itemPrice,itemQty,orderQty,totalPrice);
        }
        return combinedOrder;
    }
}
