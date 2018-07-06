create or replace PROCEDURE "SP_INSERT_LOCATION" (loc_id varchar, user_id varchar, street varchar, city varchar, state_name varchar, country varchar, zip varchar)
as
begin
  insert into locations(location_id, user_id, street, city, state, country, zip)
  values(loc_id, user_id, street, city, state_name, country, zip);

end;