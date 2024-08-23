package lk.ijse.ptobackend.dao.custom.impl;

import lk.ijse.ptobackend.dao.custom.CustomerDAO;
import lk.ijse.ptobackend.dto.CustomerDTO;
import lk.ijse.ptobackend.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    static String SAVE_CUSTOMERS = "INSERT INTO Customer VALUES (?,?,?,?)";
    static String GET_ALL_CUSTOMERS = "SELECT * FROM Customer";
    static String DELETE_CUSTOMERS = "DELETE FROM Customer WHERE customerID = ?";

    @Override
    public boolean save(Customer customer, Connection connection) throws SQLException {
        try {
            var ps = connection.prepareStatement(SAVE_CUSTOMERS);
            ps.setString(1, customer.getCustomerID());
            ps.setString(2, customer.getCustomerName());
            ps.setString(3, customer.getCustomerAddress());
            ps.setString(4, customer.getCustomerPhoneNumber());

            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public List<Customer> getAll(Connection connection) throws SQLException {
        var ps = connection.prepareStatement(GET_ALL_CUSTOMERS);
        var resultSet = ps.executeQuery();
        List<Customer> customerList = new ArrayList<>();
        while (resultSet.next()){
            Customer customers = new Customer(
                    resultSet.getString("customerID"),
                    resultSet.getString("customerName"),
                    resultSet.getString("customerAddress"),
                    resultSet.getString("customerPhoneNumber")
            );
            customerList.add(customers);
        }
        return customerList;
    }

    @Override
    public boolean update(Customer dto, Connection connection) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id, Connection connection) throws SQLException {
        try {
            var ps = connection.prepareStatement(DELETE_CUSTOMERS);
            ps.setString(1, id);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
