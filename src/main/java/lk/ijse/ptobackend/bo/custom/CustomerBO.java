package lk.ijse.ptobackend.bo.custom;

import lk.ijse.ptobackend.bo.SuperBO;
import lk.ijse.ptobackend.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO {
    boolean saveCustomer(CustomerDTO customerDTO, Connection connection) throws SQLException;

    List<CustomerDTO> getAllCustomers(Connection connection) throws SQLException;

    boolean deleteCustomer(String customerID, Connection connection) throws SQLException;

    boolean updateCustomer(String customerID, CustomerDTO customerDTO, Connection connection) throws SQLException;

    CustomerDTO searchCustomerByID(String customerID, Connection connection) throws SQLException;

    CustomerDTO searchCustomerByPhoneNumber(String customerPhoneNumber, Connection connection) throws SQLException;
}
