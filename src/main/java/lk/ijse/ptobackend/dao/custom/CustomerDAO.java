package lk.ijse.ptobackend.dao.custom;

import lk.ijse.ptobackend.dao.CrudDAO;
import lk.ijse.ptobackend.entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer> {
    Customer searchByPhone(String customerPhoneNumber, Connection connection) throws SQLException;
}
