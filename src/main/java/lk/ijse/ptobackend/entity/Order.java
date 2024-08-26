package lk.ijse.ptobackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order implements Serializable {
    private String orderID;
    private String orderDate;
    private String customerID;
}
