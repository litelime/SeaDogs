-- Procedures
CREATE OR REPLACE PROCEDURE AddOrder(OrderID VARCHAR2, UserID VARCHAR2, 
Tip NUMBER, TotalPrice NUMBER, PlacedTimestamp NUMBER, DeliveryTimestamp NUMBER,
CardID VARCHAR2, Instructions VARCHAR2, DeliveryMethodID VARCHAR2, 
StoreID VARCHAR2, DeliveryStatusID VARCHAR2)
AS
BEGIN
  INSERT INTO ORDERS (ORDER_ID, USER_ID, TIP, TOTAL_PRICE, PLACED_TIMESTAMP, 
  DELIVERY_TIMESTAMP, CARD_ID, INSTRUCTIONS, DELIVERY_METHOD_ID, STORE_ID, 
  DELIVERY_STATUS_ID)
    VALUES (OrderID, UserID, Tip, TotalPrice, PlacedTimestamp, 
    DeliveryTimestamp, CardID, Instructions, DeliveryMethodID, StoreID, 
    DeliveryStatusID);
END;
/
CREATE OR REPLACE PROCEDURE UpdateOrder(OrderID VARCHAR2, UserID VARCHAR2, 
Tip NUMBER, TotalPrice NUMBER, PlacedTimestamp NUMBER, DeliveryTimestamp NUMBER,
CardID VARCHAR2, Instructions VARCHAR2, DeliveryMethodID VARCHAR2, 
StoreID VARCHAR2, DeliveryStatusID VARCHAR2)
AS
BEGIN
  UPDATE ORDERS
  SET USER_ID=UserID, TIP=Tip, TOTAL_PRICE=TotalPrice, 
  PLACED_TIMESTAMP=PlacedTimestamp, DELIVERY_TIMESTAMP=DeliveryTimestamp, 
  CARD_ID=CardID, INSTRUCTIONS=Instructions, 
  DELIVERY_METHOD_ID=DeliveryMethodID, STORE_ID=StoreID, 
  DELIVERY_STATUS_ID=DeliveryStatusID
  WHERE ORDER_ID=OrderID;
END;
/
CREATE OR REPLACE PROCEDURE DeleteOrder(OrderID VARCHAR2)
AS
BEGIN
  DELETE FROM ORDERS 
  WHERE ORDER_ID = OrderID;
END;
/
--procedures for adding items to order_items
CREATE OR REPLACE PROCEDURE AddOrderItem(OrderID VARCHAR2, ItemID VARCHAR2)
AS
BEGIN 
  INSERT INTO ORDER_ITEMS (ORDER_ID, ITEM_ID)
  VALUES (OrderID, ItemID);
END;
/
CREATE OR REPLACE PROCEDURE DeleteOrderItems(OrderID VARCHAR2)
AS
BEGIN
  DELETE FROM ORDER_ITEMS 
  WHERE ORDER_ID = OrderID;
END;
--View compilation errors
SHOW ERRORS;
/