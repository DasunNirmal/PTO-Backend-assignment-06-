package lk.ijse.ptobackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemDTO {
    private String itemID;
    private String itemName;
    private double itemPrice;
    private int itemQty;
}
