-- Procedures
CREATE OR REPLACE PROCEDURE AddDeliveryStatus(ID VARCHAR2, Name VARCHAR2)
AS
BEGIN
  INSERT INTO DELIVERY_STATUSES (DELIVERY_STATUS_ID, DELIVERY_STATUS)
    VALUES (ID, Name);
END;
/
CREATE OR REPLACE PROCEDURE DeleteDeliveryStatus(ID VARCHAR2)
AS
BEGIN
  DELETE FROM DELIVERY_STATUSES 
  WHERE DELIVERY_STATUS_ID = ID;
END;
/
create or replace procedure sp_update_delivery_status (id varchar, status varchar)
as
begin
  update delivery_statuses set delivery_status = status 
  where delivery_status_id = id;
end;
/
