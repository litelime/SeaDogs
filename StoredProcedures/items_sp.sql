create or replace procedure updateItem(useID varchar, fname varchar, 
veg char ,item_t varchar, des varchar, timeSlot varchar,pic varchar, pric number)
as 
begin
  update items
  set name=fname, vegetarian=veg, item_type_id=item_t, description = des, time_slot_id = timeSlot, photo=pic, price=pric
  where item_id=useID;
end;
/

create or replace procedure insertItem(useID varchar, fname varchar, 
veg char ,item_t varchar, des varchar, timeSlot varchar,pic varchar, pric number)
as
begin 
	insert into items values(useID, fname,veg,item_t,des,timeSlot,pic,pric);
end;
/