package lk.ijse.ptobackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer implements Serializable {
    private String customerID;
    private String customerName;
    private String customerAddress;
    private String customerPhoneNumber;
}
