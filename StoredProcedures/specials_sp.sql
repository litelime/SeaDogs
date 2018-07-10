create or replace PROCEDURE "SP_INSERT_SPECIAL" (special_id varchar, discount_percentage number) 
as
begin
  insert into specials(special_id, discount_percentage);
end;
/

create or replace procedure sp_update_special (id varchar, discount number)
as
begin
  update specials set discount_percentage = discount 
  where item_id = id;
end;
/

create or replace procedure sp_delete_special (id varchar)
as
begin
  delete from specials where item_id = id;
end;
/