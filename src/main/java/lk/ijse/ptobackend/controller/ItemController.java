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
import lk.ijse.ptobackend.bo.custom.ItemBO;
import lk.ijse.ptobackend.dto.ItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/itemController")
public class ItemController extends HttpServlet {

    private Connection connection;
    static Logger logger = LoggerFactory.getLogger(ItemController.class);
    ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEMS);

    @Override
    public void init(ServletConfig config) throws ServletException {
        logger.info("Initializing ItemController with calling init method");
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
            ItemDTO itemDTO = jsonb.fromJson(req.getReader(), ItemDTO.class);

            boolean iSaved = itemBO.saveItem(itemDTO, connection);
            if (iSaved) {
                logger.info("Item Saved Successfully");
                writer.write("Item saved successfully");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                logger.info("Something went wrong Item did not saved successfully");
                writer.write(" Something went wrong Item did not saved successfully");
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
        if (req.getParameter("itemID") != null) {
            logger.info("GET Request With the Item ID");
            searchItem(req, resp);
        } else {
            logger.info("GET Request Without the Item ID");
            loadAllItems(req, resp);
        }
    }

    private void searchItem(HttpServletRequest req, HttpServletResponse resp) {
        var itemID = req.getParameter("itemID");
        try (var writer = resp.getWriter()){
            ItemDTO item = itemBO.searchItem(itemID,connection);
            var jsonb = JsonbBuilder.create();
            resp.setContentType("application/json");
            jsonb.toJson(item,writer);
            logger.info("Get Items by ID is Successful");
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllItems(HttpServletRequest req, HttpServletResponse resp) {
        try (var writer = resp.getWriter()) {
            List<ItemDTO> iteDTOList = itemBO.getAllItems(connection);
            if (iteDTOList != null) {
                logger.info("Get All Items Successfully");
                resp.setContentType("application/json");
                Jsonb jsonb = JsonbBuilder.create();
                jsonb.toJson(iteDTOList, writer);
            } else {
                writer.write("No items found");
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
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
            var customerID = req.getParameter("itemID");
            Jsonb jsonb = JsonbBuilder.create();
            ItemDTO itemDTO = jsonb.fromJson(req.getReader(), ItemDTO.class);
            boolean isUpdated = itemBO.updateItem(customerID, itemDTO, connection);
            if (isUpdated) {
                logger.info("Items updated successfully");
                resp.getWriter().write("Item updated successfully");
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                writer.write("Something went wrong Item did not updated successfully");
            }
        } catch (JsonbException | SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*Todo: Delete Details*/
        logger.info("DELETE Request Received");
        var itemID = req.getParameter("itemID");
        try {
            boolean isDeleted = itemBO.deleteItem(itemID,connection);
            if (isDeleted) {
                logger.info("Item deleted successfully");
                resp.getWriter().write("Item deleted successfully");
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                resp.getWriter().write("Item not deleted");
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
