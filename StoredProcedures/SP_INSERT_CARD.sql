create or replace PROCEDURE SP_INSERT_CARD (card_id varchar2, user_id varchar2, card_number number, expiry_date DATE, security_code number)
AS 
BEGIN
 insert into Cards
  values(card_id, user_id,card_number, EXPIRY_DATE, security_code);
END SP_INSERT_CARD;
/