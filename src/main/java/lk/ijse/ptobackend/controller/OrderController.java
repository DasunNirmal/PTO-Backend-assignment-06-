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
import lk.ijse.ptobackend.bo.custom.OrderBO;
import lk.ijse.ptobackend.bo.custom.OrderDetailBO;
import lk.ijse.ptobackend.dto.CombinedOrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/orderController")
public class OrderController extends HttpServlet {

    private Connection connection;
    static Logger logger = LoggerFactory.getLogger(OrderController.class);
    OrderBO orderBO = (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDERS);
    OrderDetailBO orderDetailBO = (OrderDetailBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER_DETAILS);
    ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEMS);

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
        if (!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        try(var reader = req.getReader(); var writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            CombinedOrderDTO combinedOrderDTO = jsonb.fromJson(reader, CombinedOrderDTO.class);

            boolean iSaved = orderBO.saveOrder(combinedOrderDTO, connection);
            if (iSaved) {
                writer.write("Order saved successfully");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else {
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
        super.doGet(req, resp);
    }

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*Todo: Update Details*/
        super.doPatch(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*Todo: Delete Details*/
        super.doDelete(req, resp);
    }
}
