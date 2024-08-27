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
        return false;
    }

    @Override
    public boolean delete(String id, Connection connection) throws SQLException {
        return false;
    }

    @Override
    public CombinedOrder search(String id, Connection connection) throws SQLException {
        return null;
    }
}
