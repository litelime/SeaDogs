
SHOW ERRORS;

create or replace procedure sp_update_user(user_num varchar, firstN varchar, lastN varchar, pho varchar, eml varchar, psswd varchar, u_s_id varchar)
as 
begin
  update users
  set first=firstN, last=lastN, phone=pho, email=eml, password=psswd, user_status_id=u_s_id
  where user_id=user_num;
end;
/
create or replace procedure sp_insert_user(user_id varchar, first varchar, last varchar, phone varchar, email varchar, password varchar, user_status_id varchar)
as 
begin
  insert into users
  values(user_id, first, last, phone, email, password, user_status_id);
  
end;
/

create or replace procedure sp_delete_user_by_id(user_num number)
as 
begin
  Delete from users where user_id = user_num;
end;

/
CREATE OR REPLACE PROCEDURE UPDATE_ORDER_ADMIN(orderID VARCHAR2, newTip NUMBER,
                                               newPrice NUMBER, cardID VARCHAR2,
                                               instr VARCHAR2, delID VARCHAR2,
                                               delStatus VARCHAR2) AS 
BEGIN
  UPDATE orders
  SET tip = newTip, total_price = newPrice, card_id = cardID,
      instructions = instr, delivery_method_id = delID, 
      delivery_status_id = delStatus
  WHERE order_id = orderID;
END UPDATE_ORDER_ADMIN;
/