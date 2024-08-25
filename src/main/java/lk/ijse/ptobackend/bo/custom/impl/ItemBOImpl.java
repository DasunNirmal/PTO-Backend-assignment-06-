package lk.ijse.ptobackend.bo.custom.impl;

import lk.ijse.ptobackend.bo.custom.ItemBO;
import lk.ijse.ptobackend.dao.DAOFactory;
import lk.ijse.ptobackend.dao.custom.ItemDAO;
import lk.ijse.ptobackend.dto.ItemDTO;
import lk.ijse.ptobackend.entity.Item;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBO {

    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEMS);

    @Override
    public boolean saveItem(ItemDTO itemDTO, Connection connection) throws SQLException {
        return itemDAO.save(new Item(itemDTO.getItemID(), itemDTO.getItemName(), itemDTO.getItemPrice(), itemDTO.getItemQty()), connection);
    }

    @Override
    public List<ItemDTO> getAllItems(Connection connection) throws SQLException {
        List<ItemDTO> itemDTOS = new ArrayList<>();
        List<Item> items = itemDAO.getAll(connection);
        for (Item item : items) {
            itemDTOS.add(new ItemDTO(item.getItemID(), item.getItemName(), item.getItemPrice(), item.getItemQty()));
        }
        return itemDTOS;
    }

    @Override
    public boolean updateItem(String customerID, ItemDTO itemDTO, Connection connection) throws SQLException {
        return itemDAO.update(customerID, new Item(itemDTO.getItemID(), itemDTO.getItemName(), itemDTO.getItemPrice(), itemDTO.getItemQty()), connection);
    }

    @Override
    public boolean deleteItem(String itemID, Connection connection) throws SQLException {
        return itemDAO.delete(itemID,connection);
    }

    @Override
    public ItemDTO searchItem(String itemID, Connection connection) throws SQLException {
        Item item = itemDAO.search(itemID,connection);
        if (item != null) {
            return new ItemDTO(item.getItemID(), item.getItemName(), item.getItemPrice(), item.getItemQty());
        } else {
            return null;
        }
    }
}
