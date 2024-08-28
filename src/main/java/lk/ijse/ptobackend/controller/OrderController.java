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
import lk.ijse.ptobackend.bo.custom.CombinedOrderBO;
import lk.ijse.ptobackend.bo.custom.OrderBO;
import lk.ijse.ptobackend.dto.CombinedOrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/orderController")
public class OrderController extends HttpServlet {

    private Connection connection;
    static Logger logger = LoggerFactory.getLogger(OrderController.class);
    OrderBO orderBO = (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDERS);
    CombinedOrderBO combinedOrderBO = (CombinedOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.COMBINED_ORDER);

    @Override
    public void init(ServletConfig config) throws ServletException {
        logger.info("Initializing OrderController with calling init method");
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

        try(var reader = req.getReader(); var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            CombinedOrderDTO combinedOrderDTO = jsonb.fromJson(reader, CombinedOrderDTO.class);

            boolean iSaved = orderBO.saveOrder(combinedOrderDTO, connection);
            if (iSaved) {
                logger.info("Order Saved Successfully");
                writer.write("Order saved successfully");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                logger.info("Something went wrong Order did not saved successfully");
                writer.write(" Something went wrong Order did not saved successfully");
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
        if (req.getParameter("orderID") != null) {
            logger.info("GET Request With the Order ID");
            searchOrdersByID(req, resp);
        } else {
            logger.info("GET Request Without the Order ID");
            loadAllOrders(req, resp);
        }
    }

    private void searchOrdersByID(HttpServletRequest req, HttpServletResponse resp) {
        var orderID = req.getParameter("orderID");
        try (var writer = resp.getWriter()){
            CombinedOrderDTO combinedOrder = combinedOrderBO.searchOrdersByID(orderID,connection);
            var jsonb = JsonbBuilder.create();
            resp.setContentType("application/json");
            jsonb.toJson(combinedOrder,writer);
            logger.info("Get Orders by ID is Successful");
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAllOrders(HttpServletRequest req, HttpServletResponse resp) {
        try (var writer = resp.getWriter()) {
            List<CombinedOrderDTO> combinedOrderDTOS = combinedOrderBO.getAllOrders(connection);
            if (combinedOrderDTOS != null) {
                logger.info("Get All Orders Successfully");
                resp.setContentType("application/json");
                Jsonb jsonb = JsonbBuilder.create();
                jsonb.toJson(combinedOrderDTOS, writer);
            } else {
                writer.write("No customers found");
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
        if (!"application/json".equalsIgnoreCase(req.getContentType())) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Content Type");
            return;
        }

        Jsonb jsonb = JsonbBuilder.create();
        CombinedOrderDTO combinedOrderDTO = null;
        String orderID = null;
        String itemID = null;
        String qtyOnHand = null;

        try {
            combinedOrderDTO = jsonb.fromJson(req.getReader(), CombinedOrderDTO.class);

            // Retrieve parameters from the request URL or body if needed
            orderID = req.getParameter("orderID");
            itemID = req.getParameter("itemID");
            qtyOnHand = req.getParameter("qtyOnHand");


            boolean isUpdated = combinedOrderBO.updateOrders(orderID, itemID, qtyOnHand, combinedOrderDTO, connection);

            if (isUpdated) {
                logger.info("Order updated successfully");
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                resp.getWriter().write("Order updated successfully");
            } else {
                logger.info("Order update failed");
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("Order update failed");
            }
        } catch (JsonbException | SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error processing request");
            e.printStackTrace();
        }
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*Todo: Delete Details*/
        logger.info("DELETE Request Received");
        var orderID = req.getParameter("orderID");
        var itemID = req.getParameter("itemID");
        var orderQty = req.getParameter("orderQty");
        try {
            boolean isDeleted = orderBO.deleteOrder(orderID,itemID,orderQty,connection);
            if (isDeleted) {
                logger.info("Order deleted successfully");
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                resp.getWriter().write("Order deleted successfully");
            } else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
