CREATE TABLE Customer (
                          customerID varchar(15) primary key,
                          customerName varchar(20),
                          customerAddress varchar(25),
                          customerPhoneNumber varchar(10)
);

CREATE TABLE Items (
                       itemID varchar(15) primary key,
                       itemName varchar(25),
                       itemPrice decimal(10, 2),
                       itemQty int
);