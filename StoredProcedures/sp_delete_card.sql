create or replace PROCEDURE SP_DELETE_CARD (ID varchar2)
AS 
BEGIN
    DELETE FROM Cards 
  WHERE card_id = ID;
END SP_DELETE_CARD;
/