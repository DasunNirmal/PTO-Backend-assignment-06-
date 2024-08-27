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

CREATE TABLE Orders (
                        orderID varchar(15) primary key,
                        orderDate varchar(12),
                        customerID varchar(15),
                        foreign key (customerID) references Customer(customerID) on update cascade on delete cascade
);

CREATE TABLE OrderDetails (
                              orderID varchar(15),
                              itemID varchar(15),
                              itemName varchar(25),
                              itemPrice decimal(10, 2),
                              itemQty int,
                              orderQty int,
                              totalPrice decimal(10, 2),
                              foreign key (orderID) references Orders(orderID) on update cascade on delete cascade,
                              foreign key (itemID) references Items(itemID) on update cascade on delete cascade
);

SELECT od.orderID,od.itemID,od.itemName,od.itemPrice,od.itemQty,od.orderQty,o.orderDate,o.customerID,od.totalPrice
FROM OrderDetails od
JOIN Orders o ON od.orderID = o.orderID
ORDER BY od.orderID ASC;

"SELECT od.orderID, od.itemID, od.itemName, od.itemPrice, od.itemQty, od.orderQty, o.orderDate, o.customerID, od.totalPrice " +
                                "FROM OrderDetails od " +
                                "JOIN Orders o ON od.orderID = o.orderID " +
                                "WHERE od.orderID = ? " +
                                "ORDER BY od.orderID ASC"