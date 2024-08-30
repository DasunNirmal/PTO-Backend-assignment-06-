package lk.ijse.ptobackend.dao.custom.impl;

import lk.ijse.ptobackend.dao.custom.CustomerDAO;
import lk.ijse.ptobackend.entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    static String SAVE_CUSTOMERS = "INSERT INTO Customer VALUES (?,?,?,?)";
    static String GET_ALL_CUSTOMERS = "SELECT * FROM Customer";
    static String DELETE_CUSTOMERS = "DELETE FROM Customer WHERE customerID = ?";
    static String UPDATE_CUSTOMERS = "UPDATE Customer SET customerName = ?, customerAddress = ?, customerPhoneNumber = ? WHERE customerID = ?";
    static String SEARCH_CUSTOMERS = "SELECT * FROM Customer WHERE customerID = ?";
    static String SEARCH_CUSTOMERS_BY_PHONE = "SELECT * FROM Customer WHERE customerPhoneNumber = ?";

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
    public boolean update(String id, Customer dto, Connection connection) throws SQLException {
        try {
            var ps = connection.prepareStatement(UPDATE_CUSTOMERS);
            ps.setString(1, dto.getCustomerName());
            ps.setString(2, dto.getCustomerAddress());
            ps.setString(3, dto.getCustomerPhoneNumber());
            ps.setString(4, id);
            return ps.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
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

    @Override
    public Customer search(String id, Connection connection) throws SQLException {
        Customer customer = null;
        var ps = connection.prepareStatement(SEARCH_CUSTOMERS);
        ps.setString(1, id);
        var rs = ps.executeQuery();
        while (rs.next()) {
            String customerID = rs.getString("customerID");
            String customerName = rs.getString("customerName");
            String customerAddress = rs.getString("customerAddress");
            String customerPhoneNumber = rs.getString("customerPhoneNumber");

            customer = new Customer(customerID, customerName, customerAddress, customerPhoneNumber);
        }
        return customer;
    }

    @Override
    public Customer searchByPhone(String customerPhoneNumber, Connection connection) throws SQLException {
        Customer customer = null;
        var ps = connection.prepareStatement(SEARCH_CUSTOMERS_BY_PHONE);
        ps.setString(1, customerPhoneNumber);
        var rs = ps.executeQuery();
        while (rs.next()) {
            String customerID = rs.getString("customerID");
            String customerName = rs.getString("customerName");
            String customerAddress = rs.getString("customerAddress");
            String customerPhoneNumbers = rs.getString("customerPhoneNumber");

            customer = new Customer(customerID, customerName, customerAddress, customerPhoneNumbers);
        }
        return customer;
    }
}
