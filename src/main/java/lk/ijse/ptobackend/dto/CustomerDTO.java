package lk.ijse.ptobackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDTO implements Serializable {
    private String customerID;
    private String customerName;
    private String customerAddress;
    private String customerPhoneNumber;
}
