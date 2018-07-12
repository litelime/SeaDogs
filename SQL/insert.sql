insert into time_slots
	values ('0', 21600, 39600, 'Breakfast');
insert into time_slots
	values ('1', 39600, 64800, 'Lunch');
insert into time_slots
	values ('2', 64800, 79200, 'Dinner');

insert into item_types
	values ('0', 'Drink');
insert into item_types
	values ('1', 'Side');
insert into item_types
	values ('2', 'Entree');
insert into item_types
	values ('3', 'Dessert');
	
-- item type(0 drink, 1 side, 2 entree, 3 dessert)

-- time slot (0 breakfast, 1 lunch, 2 dinner)

--		item_id,name,vegetarian,item_type_id,description,time_slot_id, photo, price
insert into items
	values ('0', 'Rice', 'y', '1', 'Steamed Basmati Rice', '1', null, 1.99);
insert into items
	values ('1', 'Samosa', 'y', '1', 'Potatoes, onions, peas, coriander, and lentils', '1', null, 4.99);
insert into items
	values ('2', 'Tandoori Chicken', 'n', '2', 'Roasted chicken marinated in yoghurt and spices ', '2', null, 9.99);
insert into items
	values ('3', 'Water', 'y', '0', 'Bottled Water', '1', null, 0.99);
insert into items
	values ('4', 'Soda', 'y', '0', 'Bubbly Molten Ice', '1', null, 1.50);
insert into items
	values ('5', 'Butter Chicken', 'n', '2', 'Chicken in a mild curry sauce', '2', null, 8.99);
insert into items
	values ('6', 'Chicken Tikka', 'n', '2', 'Marinated chicken baked on skewers', '1', null, 6.99);
insert into items
	values ('7', 'Chicken Tikka Masala', 'n', '2', 'Chicken marinated in a Yogurt tomato sauce', '2', null, 7.99);
insert into items
	values ('8', 'Strawberry Kulfi', 'y', '3', 'Traditional Indian ice cream', '2', null, 3.99);
insert into items
	values ('9', 'Mango Kulfi', 'y', '3', 'Traditional Indian ice cream', '2', null, 3.99);
insert into items
	values ('9', 'Gulab Jamun', 'y', '3', 'Fried milk balls soaked in sweet syrup', '2', null, 4.99);
insert into items
	values ('10', 'Lamb Biryani', 'n', '2', 'Basmati rice with slow cooked lamb marinated in yoghurt with Indian herbs and spices', '2', null, 13.99);
insert into items
	values ('11', 'Chicken Biryani', 'n', '2', 'Basmati rice with slow cooked chicken marinated in yoghurt with Indian herbs and spices', '2', null, 10.99);
insert into items
	values ('12', 'Sambar', 'y', '2', 'Vegetable stew cooked with a tamarind broth', '1', null, 10.99);
insert into items
	values ('13', 'Garlic Naan', 'y', '1', 'Oven-baked flatbread', '1', null, 2.99);
insert into items
	values ('14', 'Sweet Lassi', 'y', '0', 'Lassi flavoured with sugar, and strawberry fruit juice', '1', null, 1.99);
insert into items
	values ('15', 'Lamb Curry', 'n', '2', 'Lamb Curry seasoned with garlic, ginger, tomatoes and dry spices', '2', null, 13.99);
insert into items
	values ('16', 'Chicken Curry', 'n', '2', 'Chicken Curry seasoned with garlic, ginger, tomatoes and dry spices', '2', null, 11.99);

	
insert into delivery_statuses
	values ('0', 'pending');
insert into delivery_statuses
	values ('1', 'in transit');
insert into delivery_statuses
	values ('2', 'delivered');
insert into delivery_statuses
	values ('3', 'cancelled');

insert into delivery_methods
	values ('0', 'pickup');
insert into delivery_methods
	values ('1', 'delivery');
insert into delivery_methods
	values ('2', 'dine-in');

insert into user_statuses
	values ('0', 'Unverified');
insert into user_statuses
	values ('1', 'Verified');
insert into user_statuses
	values ('2', 'BANNED');
insert into user_statuses
	values ('3', 'Admin');
insert into user_statuses
	values ('4', 'Employee');
insert into user_statuses
	values ('5', 'Manager');


insert into users
	values ('0','Eric','Karnis','6035559577','eric@karnis.com','admin','1');
insert into users
	values ('1','Mindy','Larson','5069993444','man@mail.com','pass','5');
insert into users
	values ('2','Alex','Kratz','8049772556','alex@mail.com','pass','3');
insert into users
	values ('3','Bobby','Kearns','6666665566','bob@mail.com','pass','2');
insert into users
	values ('4','James','Monterrosa','5994723978','james@mail.com','pass','5');
insert into users

insert into locations
	values ('0','0',5.5,'100 example st', 'townville', 'WA', 'USA','01440');
insert into locations
	values ('1','1',5.65,'700 example st', 'wayland', 'MA', 'USA','34567');
insert into locations
	values ('2','2',2.4,'600 example st', 'billsville', 'FL', 'USA','08990');
insert into locations
	values ('3','3',2.4,'600 example st', 'bannedville', 'FL', 'USA','08990');
insert into locations
	values ('4',null,5.6,'2941 W Bell Rd', 'Phoenix', 'AZ', 'USA','85053');
insert into locations
	values ('5',null,5.6,'4515 N 16th St', 'Phoenix', 'AZ', 'USA','85016');
insert into locations
	values ('6',null,5.6,'8140 North Hayden Road', 'Scottsdale', 'AZ', 'USA','85258');
	
insert into cards
	values ('0','0',4938949496687150,(TO_DATE('2022/02/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss')) ,397);
insert into cards
	values ('1','0',4356746681933636,(TO_DATE('2021/08/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss')) ,845);
insert into cards
	values ('2','1',4373797681928422,(TO_DATE('2019/05/13 21:02:44', 'yyyy/mm/dd hh24:mi:ss')) ,235);
insert into cards
	values ('3','2',4469284391148331,(TO_DATE('2022/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss')) ,154);
insert into cards
	values ('4','3',4164777117894128,(TO_DATE('2019/06/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss')) ,900);

-- store_id, location_id, store, phone_number, manager_id, open_time, close_time
insert into stores
	values ('0','4','Deer Valley',5209057689,1,21600,79200);
insert into stores
	values ('1','5','South Phoenix',9094827459,1,21600,79200);
insert into stores
	values ('2','6','Scottsdale',5201932948,4,21600,79200);

