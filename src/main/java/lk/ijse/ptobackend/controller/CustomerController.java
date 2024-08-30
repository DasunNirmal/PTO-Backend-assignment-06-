package lk.ijse.ptobackend.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.ptobackend.bo.BOFactory;
import lk.ijse.ptobackend.bo.custom.CustomerBO;
import lk.ijse.ptobackend.dto.CustomerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/customerController")
public class CustomerController extends HttpServlet {

    private Connection connection;
    static Logger logger = LoggerFactory.getLogger(CustomerController.class);
    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMERS);

    @Override
    public void init(ServletConfig config) throws ServletException {
        logger.info("Initializing CustomerController with calling init method");
        try {
            var ctx = new InitialContext();
            DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/ptoBackend");
            this.connection = pool.getConnection();
        } catch (NamingException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*Todo: Save Details*/
        logger.info("POST Request Received");
        if (!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        try(var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);

            boolean iSaved = customerBO.saveCustomer(customerDTO, connection);
            if (iSaved) {
                logger.info("Customer Saved Successfully");
                writer.write("Customer saved successfully");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                logger.info("Something went wrong Customer did not saved successfully");
                writer.write(" Something went wrong Customer did not saved successfully");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (JsonbException | SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*Todo: Get Details*/
        if (req.getParameter("customerID") != null) {
            logger.info("GET Request With the Customer ID");
            searchCustomersByID(req, resp);
        } else if (req.getParameter("customerPhoneNumber") != null) {
            logger.info("GET Request With the Customer Phone Number");
            searchCustomersByPhoneNumber(req, resp);
        } else {
            logger.info("GET Request Without the Customer ID");
            loadAllCustomers(req, resp);
        }
    }

    private void searchCustomersByPhoneNumber(HttpServletRequest req, HttpServletResponse resp) {
        var customerPhoneNumber = req.getParameter("customerPhoneNumber");
        try (var writer = resp.getWriter()){
            CustomerDTO customer = customerBO.searchCustomerByPhoneNumber(customerPhoneNumber,connection);
            var jsonb = JsonbBuilder.create();
            resp.setContentType("application/json");
            jsonb.toJson(customer,writer);
            logger.info("Get Customers by Phone Number is Successful");
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllCustomers(HttpServletRequest req, HttpServletResponse resp) {
        try (var writer = resp.getWriter()) {
            List<CustomerDTO> customerDTOList = customerBO.getAllCustomers(connection);
            if (customerDTOList != null) {
                logger.info("Get All Customers Successfully");
                resp.setContentType("application/json");
                Jsonb jsonb = JsonbBuilder.create();
                jsonb.toJson(customerDTOList, writer);
            } else {
                writer.write("No customers found");
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void searchCustomersByID(HttpServletRequest req, HttpServletResponse resp) {
        var customerID = req.getParameter("customerID");
        try (var writer = resp.getWriter()){
            CustomerDTO customer = customerBO.searchCustomerByID(customerID,connection);
            var jsonb = JsonbBuilder.create();
            resp.setContentType("application/json");
            jsonb.toJson(customer,writer);
            logger.info("Get Customers by ID is Successful");
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*Todo: Update Details*/
        logger.info("PATCH Request Received");
        if (!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        try(var writer = resp.getWriter()) {
            var customerID = req.getParameter("customerID");
            Jsonb jsonb = JsonbBuilder.create();
            CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);
            boolean isUpdated = customerBO.updateCustomer(customerID, customerDTO, connection);
            if (isUpdated) {
                logger.info("Customer updated successfully");
                resp.getWriter().write("Customer updated successfully");
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                writer.write("Something went wrong Customer did not updated successfully");
            }
        } catch (JsonbException | SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*Todo: Delete Details*/
        logger.info("DELETE Request Received");
        var customerID = req.getParameter("customerID");
        try {
            boolean isDeleted = customerBO.deleteCustomer(customerID,connection);
            if (isDeleted) {
                logger.info("Customer deleted successfully");
                resp.getWriter().write("Customer deleted successfully");
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                resp.getWriter().write("Customer not deleted");
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
