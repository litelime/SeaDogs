create or replace PROCEDURE SP_INSERT_CARD (card_id varchar2, user_id varchar2, card_number number, expiry_date DATE, security_code number)
AS 
BEGIN
 insert into Cards
  values(card_id, user_id,card_number, EXPIRY_DATE, security_code);
END SP_INSERT_CARD;
/

create or replace PROCEDURE SP_DELETE_CARD (ID varchar2)
AS 
BEGIN
    DELETE FROM Cards 
  WHERE card_id = ID;
END SP_DELETE_CARD;
/

create or replace PROCEDURE SP_Update_CARD (nCard_id varchar2, nUser_id varchar2, nCard_number number, nEXPIRY_DATE DATE, nSecurity_Code number)
AS 
BEGIN
 update Cards set card_number = nCard_number, EXPIRY_DATE = nEXPIRY_DATE, security_code = nSecurity_Code
 where card_id = nCard_id;
END SP_UPDATE_CARD;
/