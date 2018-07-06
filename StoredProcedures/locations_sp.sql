create or replace procedure sp_insert_location(location_id varchar, user_id varchar, tax_rate number,
street varchar, city varchar, state_name varchar, country varchar, zip varchar)
as 
begin
    insert into locations
  values(location_id, user_id, tax_rate, street, city, state_name, country, zip);
end sp_insert_location;
/
create or replace procedure sp_update_location(location_id_in varchar, user_id_in varchar, tax_rate_in number,
street_in varchar, city_in varchar, state_name_in varchar, country_in varchar, zip_in varchar)
as 
begin
    update locations
  set user_id = user_id_in,
  tax_rate = tax_rate_in,
  street = street_in,
  city = city_in,
  state_name = state_name_in,
  country = country_in,
  zip = zip_in
  where location_id=location_id_in;
end sp_update_location;
/
create or replace procedure sp_delete_location(location_id_in varchar)
as
begin
  delete from locations
  where location_id = location_id_in;
end sp_delete_location;
/
--execute SP_INSERT_LOCATION('4', '4', 20.0, 'QWE', 'QWE', 'MN', 'QWE', '01440');