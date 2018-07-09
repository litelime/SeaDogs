create or replace procedure sp_insert_user_status(user_status_id number, user_status varchar)
as 
begin
  insert into user_statuses
  values(user_status_id, user_status);
  
end sp_insert_user_status;
/
create or replace procedure sp_update_user_status(user_status_num number, user_status_name varchar)
as 
begin
  update user_statuses
  set user_status=user_status_name
  where user_status_id=user_status_num;
end;
/
create or replace procedure sp_delete_user_status_by_id(user_status_num number)
as 
begin
  Delete from user_statuses where user_status_id = user_status_num;
end sp_delete_user_status_by_id;
/
create or replace PROCEDURE SP_SWAP_STATUS(old VARCHAR2, new VARCHAR2)
AS 
BEGIN
  UPDATE users
  SET user_status_id = new
  WHERE user_status_id = old;
END SP_SWAP_STATUS;
/
