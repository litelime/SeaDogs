create or replace PROCEDURE SP_Update_CARD (nCard_id varchar2, nUser_id varchar2, nCard_number number, nEXPIRY_DATE DATE, nSecurity_Code number)
AS 
BEGIN
 update Cards set card_number = nCard_number, EXPIRY_DATE = nEXPIRY_DATE, security_code = nSecurity_Code
 where card_id = nCard_id;
END SP_UPDATE_CARD;
/