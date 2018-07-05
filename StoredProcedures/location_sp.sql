create or replace procedure sp_insert_location(location_id varchar, user_id varchar, tax_rate number,
street varchar, city varchar, state_name varchar, country varchar, zip varchar)
as 
begin
    insert into locations
  values(location_id, user_id, tax_rate, street, city, state_name, country, zip);
end sp_insert_location;
/
create or replace procedure sp_update_location(location_id varchar, user_id varchar, tax_rate number,
street varchar, city varchar, state_name varchar, country varchar, zip varchar)
as 
begin
    insert into locations
  values(location_id, user_id, tax_rate, street, city, state_name, country, zip);
end sp_update_location;
/
--execute SP_INSERT_LOCATION('4', '4', 20.0, 'QWE', 'QWE', 'MN', 'QWE', '01440');