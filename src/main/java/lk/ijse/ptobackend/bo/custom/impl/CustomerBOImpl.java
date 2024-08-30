package lk.ijse.ptobackend.bo.custom.impl;

import lk.ijse.ptobackend.bo.custom.CustomerBO;
import lk.ijse.ptobackend.dao.DAOFactory;
import lk.ijse.ptobackend.dao.custom.CustomerDAO;
import lk.ijse.ptobackend.dto.CustomerDTO;
import lk.ijse.ptobackend.entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMERS);

    @Override
    public boolean saveCustomer(CustomerDTO customerDTO, Connection connection) throws SQLException {
        return customerDAO.save(new Customer(customerDTO.getCustomerID(), customerDTO.getCustomerName(), customerDTO.getCustomerAddress(), customerDTO.getCustomerPhoneNumber()), connection);
    }

    @Override
    public List<CustomerDTO> getAllCustomers(Connection connection) throws SQLException {
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        List<Customer> customers = customerDAO.getAll(connection);
        for (Customer customer : customers) {
            customerDTOS.add(new CustomerDTO(customer.getCustomerID(), customer.getCustomerName(), customer.getCustomerAddress(), customer.getCustomerPhoneNumber()));
        }
        return customerDTOS;
    }

    @Override
    public boolean deleteCustomer(String customerID, Connection connection) throws SQLException {
        return customerDAO.delete(customerID,connection);
    }

    @Override
    public boolean updateCustomer(String customerID, CustomerDTO customerDTO, Connection connection) throws SQLException {
        return customerDAO.update(customerID,
                new Customer(customerDTO.getCustomerID(), customerDTO.getCustomerName(), customerDTO.getCustomerAddress(), customerDTO.getCustomerPhoneNumber()), connection);
    }

    @Override
    public CustomerDTO searchCustomerByID(String customerID, Connection connection) throws SQLException {
        Customer customer = customerDAO.search(customerID,connection);
        if (customer != null) {
            return new CustomerDTO(customer.getCustomerID(), customer.getCustomerName(), customer.getCustomerAddress(), customer.getCustomerPhoneNumber());
        } else {
            return null;
        }
    }

    @Override
    public CustomerDTO searchCustomerByPhoneNumber(String customerPhoneNumber, Connection connection) throws SQLException {
        Customer customer = customerDAO.searchByPhone(customerPhoneNumber,connection);
        if (customer != null) {
            return new CustomerDTO(customer.getCustomerID(), customer.getCustomerName(), customer.getCustomerAddress(), customer.getCustomerPhoneNumber());
        } else {
            return null;
        }
    }
}
