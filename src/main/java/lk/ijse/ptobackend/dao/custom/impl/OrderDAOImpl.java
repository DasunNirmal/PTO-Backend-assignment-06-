package lk.ijse.ptobackend.dao.custom.impl;

import lk.ijse.ptobackend.dao.custom.OrderDAO;
import lk.ijse.ptobackend.entity.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    static String SAVE_ORDERS = "INSERT INTO Orders VALUES (?,?,?)";
    static String DELETE_ORDERS = "DELETE FROM Orders WHERE OrderID = ?";

    @Override
    public boolean save(Order order, Connection connection) throws SQLException {
        try {
            var ps = connection.prepareStatement(SAVE_ORDERS);
            ps.setString(1, order.getOrderID());
            ps.setString(2, order.getOrderDate());
            ps.setString(3, order.getCustomerID());

            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public List<Order> getAll(Connection connection) throws SQLException {
        return List.of();
    }

    @Override
    public boolean update(String id, Order dto, Connection connection) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id, Connection connection) throws SQLException {
        try {
            var ps = connection.prepareStatement(DELETE_ORDERS);
            ps.setString(1, id);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Order search(String id, Connection connection) throws SQLException {
        return null;
    }
}
