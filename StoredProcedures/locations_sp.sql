create or replace procedure sp_update_location(location_id_in varchar, user_id_in varchar,
street_in varchar, city_in varchar, state_name_in varchar, country_in varchar, zip_in varchar)
as 
begin
    update locations
  set user_id = user_id_in,
  street = street_in,
  city = city_in,
  state = state_name_in,
  country = country_in,
  zip = zip_in
  where location_id=location_id_in;
end sp_update_location;
/
create or replace PROCEDURE "SP_INSERT_LOCATION" (loc_id varchar, user_id varchar, street varchar, city varchar, state_name varchar, country varchar, zip varchar)
as
begin
  insert into locations(location_id, user_id, street, city, state, country, zip)
  values(loc_id, user_id, street, city, state_name, country, zip);

end;
/
create or replace PROCEDURE "SP_DELETE_LOCATION_BY_ID" (LOC_ID VARCHAR2)
AS
BEGIN
  DELETE FROM LOCATIONS
  WHERE LOCATION_ID = LOC_ID;
END;
/
--execute SP_INSERT_LOCATION('4', '4', 20.0, 'QWE', 'QWE', 'MN', 'QWE', '01440');