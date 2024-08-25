package lk.ijse.ptobackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Item {
    private String itemID;
    private String itemName;
    private double itemPrice;
    private int itemQty;
}
