package lk.ijse.ptobackend.dao.custom.impl;

import lk.ijse.ptobackend.dao.custom.OrderDetailDAO;
import lk.ijse.ptobackend.entity.OrderDetails;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {

    static String SAVE_ORDER_DETAILS = "INSERT INTO OrderDetails VALUES (?,?,?,?,?,?,?)";

    @Override
    public boolean save(OrderDetails orderDetails, Connection connection) throws SQLException {
        try {
            var ps = connection.prepareStatement(SAVE_ORDER_DETAILS);
            ps.setString(1, orderDetails.getOrderID());
            ps.setString(2, orderDetails.getItemID());
            ps.setString(3, orderDetails.getItemName());
            ps.setDouble(4, orderDetails.getItemPrice());
            ps.setDouble(5, orderDetails.getItemQty());
            ps.setInt(6, orderDetails.getOrderQty());
            ps.setDouble(7, orderDetails.getTotalPrice());

            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public List<OrderDetails> getAll(Connection connection) throws SQLException {
        return List.of();
    }

    @Override
    public boolean update(String id, OrderDetails dto, Connection connection) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id, Connection connection) throws SQLException {
        return false;
    }

    @Override
    public OrderDetails search(String id, Connection connection) throws SQLException {
        return null;
    }
}
