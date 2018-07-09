create or replace PROCEDURE "SP_INSERT_SPECIAL" (special_id varchar, special_name varchar, special_description varchar, item_id varchar, discount_percentage number, new_price number, photo varchar, veg varchar, time_slot varchar)
as
begin
  insert into specials(special_id, special_name, special_description, item_id, discount_percentage, new_price, photo, veg, time_slot)
  values(special_id, special_name, special_description, item_id, discount_percentage, new_price, photo, veg, time_slot);
end;
/
