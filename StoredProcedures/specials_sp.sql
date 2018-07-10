create or replace PROCEDURE SP_INSERT_SPECIAL (item_id varchar, discount_percentage number, special_id varchar, special_name varchar, special_description varchar, amount number, photo varchar, veg varchar, time_slot varchar) 
as
begin
  insert into specials(item_id, discount_percentage, special_id, special_name, special_description, amount, photo, veg, time_slot)
  values(item_id, discount_percentage, special_id, special_name, special_description, amount, photo, veg, time_slot);
end;
/

create or replace procedure sp_update_special (i_id varchar, dp number, s_id varchar, s_name varchar, s_description varchar, amount_in number, pic varchar, vegi varchar, t_slot varchar)
as
begin
  update specials
  set discount_percentage = dp,
  special_name = s_name,
  special_description = s_description,
  amount = amount_in,
  photo = pic,
  veg = vegi,
  time_slot = t_slot
  where special_id = s_id
  and item_id = i_id;
end;
/

create or replace procedure sp_delete_special (id varchar)
as
begin
  delete from specials where item_id = id;
end;
/