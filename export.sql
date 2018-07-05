--------------------------------------------------------
--  File created - Thursday-July-05-2018   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table CARDS
--------------------------------------------------------

  CREATE TABLE "DB_USPRING"."CARDS" 
   (	"CARD_ID" VARCHAR2(4000 BYTE), 
	"USER_ID" VARCHAR2(4000 BYTE), 
	"CARD_NUMBER" NUMBER(*,0), 
	"EXPIRY_DATE" DATE, 
	"SECURITY_CODE" NUMBER(*,0)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEW_TABLESPACESPRING2" ;
--------------------------------------------------------
--  DDL for Table DELIVERY_METHODS
--------------------------------------------------------

  CREATE TABLE "DB_USPRING"."DELIVERY_METHODS" 
   (	"DELIVERY_METHOD_ID" VARCHAR2(4000 BYTE), 
	"DELIVERY_METHOD" VARCHAR2(4000 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEW_TABLESPACESPRING2" ;
--------------------------------------------------------
--  DDL for Table DELIVERY_STATUSES
--------------------------------------------------------

  CREATE TABLE "DB_USPRING"."DELIVERY_STATUSES" 
   (	"DELIVERY_STATUS_ID" VARCHAR2(4000 BYTE), 
	"DELIVERY_STATUS" VARCHAR2(4000 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEW_TABLESPACESPRING2" ;
--------------------------------------------------------
--  DDL for Table ITEMS
--------------------------------------------------------

  CREATE TABLE "DB_USPRING"."ITEMS" 
   (	"ITEM_ID" VARCHAR2(4000 BYTE), 
	"NAME" VARCHAR2(4000 BYTE), 
	"VEGETARIAN" CHAR(1 BYTE), 
	"ITEM_TYPE_ID" VARCHAR2(4000 BYTE), 
	"DESCRIPTION" VARCHAR2(4000 BYTE), 
	"TIME_SLOT_ID" VARCHAR2(4000 BYTE), 
	"PHOTO" VARCHAR2(4000 BYTE), 
	"PRICE" NUMBER(5,2)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEW_TABLESPACESPRING2" ;
--------------------------------------------------------
--  DDL for Table ITEM_TYPES
--------------------------------------------------------

  CREATE TABLE "DB_USPRING"."ITEM_TYPES" 
   (	"ITEM_TYPE_ID" VARCHAR2(4000 BYTE), 
	"ITEM_TYPE" VARCHAR2(4000 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEW_TABLESPACESPRING2" ;
--------------------------------------------------------
--  DDL for Table LOCATIONS
--------------------------------------------------------

  CREATE TABLE "DB_USPRING"."LOCATIONS" 
   (	"LOCATION_ID" VARCHAR2(4000 BYTE), 
	"USER_ID" VARCHAR2(4000 BYTE), 
	"TAX_RATE" NUMBER(5,2), 
	"STREET" VARCHAR2(4000 BYTE), 
	"CITY" VARCHAR2(4000 BYTE), 
	"STATE" VARCHAR2(4000 BYTE), 
	"COUNTRY" VARCHAR2(4000 BYTE), 
	"ZIP" VARCHAR2(4000 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEW_TABLESPACESPRING2" ;
--------------------------------------------------------
--  DDL for Table ORDERS
--------------------------------------------------------

  CREATE TABLE "DB_USPRING"."ORDERS" 
   (	"ORDER_ID" VARCHAR2(4000 BYTE), 
	"USER_ID" VARCHAR2(4000 BYTE), 
	"TIP" NUMBER(5,2), 
	"TOTAL_PRICE" NUMBER(7,2), 
	"PLACED_TIMESTAMP" NUMBER(*,0), 
	"DELIVERY_TIMESTAMP" NUMBER(*,0), 
	"CARD_ID" VARCHAR2(4000 BYTE), 
	"INSTRUCTIONS" VARCHAR2(4000 BYTE), 
	"DELIVERY_METHOD_ID" VARCHAR2(4000 BYTE), 
	"STORE_ID" VARCHAR2(4000 BYTE), 
	"DELIVERY_STATUS_ID" VARCHAR2(4000 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEW_TABLESPACESPRING2" ;
--------------------------------------------------------
--  DDL for Table ORDER_ITEMS
--------------------------------------------------------

  CREATE TABLE "DB_USPRING"."ORDER_ITEMS" 
   (	"ORDER_ID" VARCHAR2(4000 BYTE), 
	"ITEM_ID" VARCHAR2(4000 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEW_TABLESPACESPRING2" ;
--------------------------------------------------------
--  DDL for Table SPECIALS
--------------------------------------------------------

  CREATE TABLE "DB_USPRING"."SPECIALS" 
   (	"ITEM_ID" VARCHAR2(4000 BYTE), 
	"DISCOUNT_PERCENTAGE" NUMBER(*,0)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEW_TABLESPACESPRING2" ;
--------------------------------------------------------
--  DDL for Table STORES
--------------------------------------------------------

  CREATE TABLE "DB_USPRING"."STORES" 
   (	"STORE_ID" VARCHAR2(4000 BYTE), 
	"LOCATION_ID" VARCHAR2(4000 BYTE), 
	"STORE" VARCHAR2(4000 BYTE), 
	"PHONE_NUMBER" NUMBER(*,0), 
	"MANAGER_ID" VARCHAR2(4000 BYTE), 
	"OPEN_TIME" NUMBER(*,0), 
	"CLOSE_TIME" NUMBER(*,0)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEW_TABLESPACESPRING2" ;
--------------------------------------------------------
--  DDL for Table TIME_SLOTS
--------------------------------------------------------

  CREATE TABLE "DB_USPRING"."TIME_SLOTS" 
   (	"TIME_SLOT_ID" VARCHAR2(4000 BYTE), 
	"SLOT_START" NUMBER(*,0), 
	"SLOT_END" NUMBER(*,0), 
	"SLOT_NAME" VARCHAR2(4000 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEW_TABLESPACESPRING2" ;
--------------------------------------------------------
--  DDL for Table USERS
--------------------------------------------------------

  CREATE TABLE "DB_USPRING"."USERS" 
   (	"USER_ID" VARCHAR2(4000 BYTE), 
	"FIRST" VARCHAR2(4000 BYTE), 
	"LAST" VARCHAR2(4000 BYTE), 
	"PHONE" VARCHAR2(4000 BYTE), 
	"EMAIL" VARCHAR2(4000 BYTE), 
	"PASSWORD" VARCHAR2(4000 BYTE), 
	"USER_STATUS_ID" VARCHAR2(4000 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEW_TABLESPACESPRING2" ;
--------------------------------------------------------
--  DDL for Table USER_STATUSES
--------------------------------------------------------

  CREATE TABLE "DB_USPRING"."USER_STATUSES" 
   (	"USER_STATUS_ID" VARCHAR2(4000 BYTE), 
	"USER_STATUS" VARCHAR2(4000 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEW_TABLESPACESPRING2" ;
REM INSERTING into DB_USPRING.CARDS
SET DEFINE OFF;
Insert into DB_USPRING.CARDS (CARD_ID,USER_ID,CARD_NUMBER,EXPIRY_DATE,SECURITY_CODE) values ('0','0',5,to_date('03-MAY-03','DD-MON-RR'),123);
Insert into DB_USPRING.CARDS (CARD_ID,USER_ID,CARD_NUMBER,EXPIRY_DATE,SECURITY_CODE) values ('1','0',5435,to_date('03-MAY-03','DD-MON-RR'),123);
Insert into DB_USPRING.CARDS (CARD_ID,USER_ID,CARD_NUMBER,EXPIRY_DATE,SECURITY_CODE) values ('2','1',523453,to_date('03-MAY-03','DD-MON-RR'),123);
Insert into DB_USPRING.CARDS (CARD_ID,USER_ID,CARD_NUMBER,EXPIRY_DATE,SECURITY_CODE) values ('3','2',32545,to_date('03-MAY-03','DD-MON-RR'),123);
Insert into DB_USPRING.CARDS (CARD_ID,USER_ID,CARD_NUMBER,EXPIRY_DATE,SECURITY_CODE) values ('4','3',642645,to_date('03-MAY-03','DD-MON-RR'),123);
REM INSERTING into DB_USPRING.DELIVERY_METHODS
SET DEFINE OFF;
Insert into DB_USPRING.DELIVERY_METHODS (DELIVERY_METHOD_ID,DELIVERY_METHOD) values ('0','pickup');
Insert into DB_USPRING.DELIVERY_METHODS (DELIVERY_METHOD_ID,DELIVERY_METHOD) values ('1','delivery');
Insert into DB_USPRING.DELIVERY_METHODS (DELIVERY_METHOD_ID,DELIVERY_METHOD) values ('2','dine-in');
REM INSERTING into DB_USPRING.DELIVERY_STATUSES
SET DEFINE OFF;
Insert into DB_USPRING.DELIVERY_STATUSES (DELIVERY_STATUS_ID,DELIVERY_STATUS) values ('0','pending');
Insert into DB_USPRING.DELIVERY_STATUSES (DELIVERY_STATUS_ID,DELIVERY_STATUS) values ('1','on delivery');
Insert into DB_USPRING.DELIVERY_STATUSES (DELIVERY_STATUS_ID,DELIVERY_STATUS) values ('2','delivered');
Insert into DB_USPRING.DELIVERY_STATUSES (DELIVERY_STATUS_ID,DELIVERY_STATUS) values ('3','cancelled');
REM INSERTING into DB_USPRING.ITEMS
SET DEFINE OFF;
Insert into DB_USPRING.ITEMS (ITEM_ID,NAME,VEGETARIAN,ITEM_TYPE_ID,DESCRIPTION,TIME_SLOT_ID,PHOTO,PRICE) values ('0','Rice','y','1','Steamed Basmati Rice','0',null,1);
Insert into DB_USPRING.ITEMS (ITEM_ID,NAME,VEGETARIAN,ITEM_TYPE_ID,DESCRIPTION,TIME_SLOT_ID,PHOTO,PRICE) values ('1','Beans','y','1','Can of Beans','1',null,1.2);
Insert into DB_USPRING.ITEMS (ITEM_ID,NAME,VEGETARIAN,ITEM_TYPE_ID,DESCRIPTION,TIME_SLOT_ID,PHOTO,PRICE) values ('2','Rice and Beans','y','3','The Rice and the Beans','0',null,2);
Insert into DB_USPRING.ITEMS (ITEM_ID,NAME,VEGETARIAN,ITEM_TYPE_ID,DESCRIPTION,TIME_SLOT_ID,PHOTO,PRICE) values ('3','Water','y','0','Molten Ice','1',null,0.5);
Insert into DB_USPRING.ITEMS (ITEM_ID,NAME,VEGETARIAN,ITEM_TYPE_ID,DESCRIPTION,TIME_SLOT_ID,PHOTO,PRICE) values ('4','Soda','y','0','Bubbly Molten Ice','1',null,0.7);
Insert into DB_USPRING.ITEMS (ITEM_ID,NAME,VEGETARIAN,ITEM_TYPE_ID,DESCRIPTION,TIME_SLOT_ID,PHOTO,PRICE) values ('5','Chicken','n','2','Pan Friend Chicken','2',null,6.99);
REM INSERTING into DB_USPRING.ITEM_TYPES
SET DEFINE OFF;
Insert into DB_USPRING.ITEM_TYPES (ITEM_TYPE_ID,ITEM_TYPE) values ('0','Drink');
Insert into DB_USPRING.ITEM_TYPES (ITEM_TYPE_ID,ITEM_TYPE) values ('1','Side');
Insert into DB_USPRING.ITEM_TYPES (ITEM_TYPE_ID,ITEM_TYPE) values ('2','Entree');
Insert into DB_USPRING.ITEM_TYPES (ITEM_TYPE_ID,ITEM_TYPE) values ('3','Package');
REM INSERTING into DB_USPRING.LOCATIONS
SET DEFINE OFF;
Insert into DB_USPRING.LOCATIONS (LOCATION_ID,USER_ID,TAX_RATE,STREET,CITY,STATE,COUNTRY,ZIP) values ('0','0',50.5,'100 example st','townville','WA','USA','01440');
Insert into DB_USPRING.LOCATIONS (LOCATION_ID,USER_ID,TAX_RATE,STREET,CITY,STATE,COUNTRY,ZIP) values ('1','1',56.5,'700 example st','wayland','MA','USA','34567');
Insert into DB_USPRING.LOCATIONS (LOCATION_ID,USER_ID,TAX_RATE,STREET,CITY,STATE,COUNTRY,ZIP) values ('2','2',20.4,'600 example st','billsville','FL','USA','08990');
Insert into DB_USPRING.LOCATIONS (LOCATION_ID,USER_ID,TAX_RATE,STREET,CITY,STATE,COUNTRY,ZIP) values ('3','3',20.4,'600 example st','bannedville','FL','USA','08990');
REM INSERTING into DB_USPRING.ORDERS
SET DEFINE OFF;
Insert into DB_USPRING.ORDERS (ORDER_ID,USER_ID,TIP,TOTAL_PRICE,PLACED_TIMESTAMP,DELIVERY_TIMESTAMP,CARD_ID,INSTRUCTIONS,DELIVERY_METHOD_ID,STORE_ID,DELIVERY_STATUS_ID) values ('0','0',2.5,15.5,0,0,'0','hey','0','0','0');
Insert into DB_USPRING.ORDERS (ORDER_ID,USER_ID,TIP,TOTAL_PRICE,PLACED_TIMESTAMP,DELIVERY_TIMESTAMP,CARD_ID,INSTRUCTIONS,DELIVERY_METHOD_ID,STORE_ID,DELIVERY_STATUS_ID) values ('1','1',5,16.5,0,0,'0','run to house','1','0','2');
REM INSERTING into DB_USPRING.ORDER_ITEMS
SET DEFINE OFF;
Insert into DB_USPRING.ORDER_ITEMS (ORDER_ID,ITEM_ID) values ('0','0');
Insert into DB_USPRING.ORDER_ITEMS (ORDER_ID,ITEM_ID) values ('1','2');
Insert into DB_USPRING.ORDER_ITEMS (ORDER_ID,ITEM_ID) values ('1','3');
Insert into DB_USPRING.ORDER_ITEMS (ORDER_ID,ITEM_ID) values ('1','4');
REM INSERTING into DB_USPRING.SPECIALS
SET DEFINE OFF;
Insert into DB_USPRING.SPECIALS (ITEM_ID,DISCOUNT_PERCENTAGE) values ('0',10);
Insert into DB_USPRING.SPECIALS (ITEM_ID,DISCOUNT_PERCENTAGE) values ('4',35);
REM INSERTING into DB_USPRING.STORES
SET DEFINE OFF;
Insert into DB_USPRING.STORES (STORE_ID,LOCATION_ID,STORE,PHONE_NUMBER,MANAGER_ID,OPEN_TIME,CLOSE_TIME) values ('0','0','Deer Valley',1,'0',800,2100);
REM INSERTING into DB_USPRING.TIME_SLOTS
SET DEFINE OFF;
Insert into DB_USPRING.TIME_SLOTS (TIME_SLOT_ID,SLOT_START,SLOT_END,SLOT_NAME) values ('0',600,900,'Breakfast');
Insert into DB_USPRING.TIME_SLOTS (TIME_SLOT_ID,SLOT_START,SLOT_END,SLOT_NAME) values ('1',600,900,'Lunch');
Insert into DB_USPRING.TIME_SLOTS (TIME_SLOT_ID,SLOT_START,SLOT_END,SLOT_NAME) values ('2',600,900,'Dinner');
REM INSERTING into DB_USPRING.USERS
SET DEFINE OFF;
Insert into DB_USPRING.USERS (USER_ID,FIRST,LAST,PHONE,EMAIL,PASSWORD,USER_STATUS_ID) values ('4728.009688548512','David','Santana','9095497693','david@mail.com','pass','1');
Insert into DB_USPRING.USERS (USER_ID,FIRST,LAST,PHONE,EMAIL,PASSWORD,USER_STATUS_ID) values ('0','Eric','Karnis','6035559577','eric@karnis.com','hey','0');
Insert into DB_USPRING.USERS (USER_ID,FIRST,LAST,PHONE,EMAIL,PASSWORD,USER_STATUS_ID) values ('1','Mindy','Manager','5069993444','man@mail.com','pass','5');
Insert into DB_USPRING.USERS (USER_ID,FIRST,LAST,PHONE,EMAIL,PASSWORD,USER_STATUS_ID) values ('2','Alex','Admin','8049772556','alex@mail.com','pass','3');
Insert into DB_USPRING.USERS (USER_ID,FIRST,LAST,PHONE,EMAIL,PASSWORD,USER_STATUS_ID) values ('3','Bobby','Banned','6666665566','bob@mail.com','pass','2');
REM INSERTING into DB_USPRING.USER_STATUSES
SET DEFINE OFF;
Insert into DB_USPRING.USER_STATUSES (USER_STATUS_ID,USER_STATUS) values ('0','Unverified');
Insert into DB_USPRING.USER_STATUSES (USER_STATUS_ID,USER_STATUS) values ('1','Verified');
Insert into DB_USPRING.USER_STATUSES (USER_STATUS_ID,USER_STATUS) values ('2','BANNED');
Insert into DB_USPRING.USER_STATUSES (USER_STATUS_ID,USER_STATUS) values ('3','Admin');
Insert into DB_USPRING.USER_STATUSES (USER_STATUS_ID,USER_STATUS) values ('4','Employee');
Insert into DB_USPRING.USER_STATUSES (USER_STATUS_ID,USER_STATUS) values ('5','Manager');
--------------------------------------------------------
--  Constraints for Table DELIVERY_STATUSES
--------------------------------------------------------

  ALTER TABLE "DB_USPRING"."DELIVERY_STATUSES" ADD PRIMARY KEY ("DELIVERY_STATUS_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 167 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEW_TABLESPACESPRING2"  ENABLE;
--------------------------------------------------------
--  Constraints for Table USERS
--------------------------------------------------------

  ALTER TABLE "DB_USPRING"."USERS" ADD UNIQUE ("EMAIL")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 167 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEW_TABLESPACESPRING2"  ENABLE;
  ALTER TABLE "DB_USPRING"."USERS" ADD PRIMARY KEY ("USER_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 167 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEW_TABLESPACESPRING2"  ENABLE;
  ALTER TABLE "DB_USPRING"."USERS" MODIFY ("PASSWORD" NOT NULL ENABLE);
  ALTER TABLE "DB_USPRING"."USERS" MODIFY ("EMAIL" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table TIME_SLOTS
--------------------------------------------------------

  ALTER TABLE "DB_USPRING"."TIME_SLOTS" ADD PRIMARY KEY ("TIME_SLOT_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 167 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEW_TABLESPACESPRING2"  ENABLE;
--------------------------------------------------------
--  Constraints for Table CARDS
--------------------------------------------------------

  ALTER TABLE "DB_USPRING"."CARDS" ADD PRIMARY KEY ("CARD_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 167 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEW_TABLESPACESPRING2"  ENABLE;
--------------------------------------------------------
--  Constraints for Table LOCATIONS
--------------------------------------------------------

  ALTER TABLE "DB_USPRING"."LOCATIONS" ADD PRIMARY KEY ("LOCATION_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 167 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEW_TABLESPACESPRING2"  ENABLE;
  ALTER TABLE "DB_USPRING"."LOCATIONS" ADD CONSTRAINT "LIMIT_TAX" CHECK (tax_rate between 0 and 100.00) ENABLE;
--------------------------------------------------------
--  Constraints for Table STORES
--------------------------------------------------------

  ALTER TABLE "DB_USPRING"."STORES" ADD PRIMARY KEY ("STORE_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 167 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEW_TABLESPACESPRING2"  ENABLE;
--------------------------------------------------------
--  Constraints for Table DELIVERY_METHODS
--------------------------------------------------------

  ALTER TABLE "DB_USPRING"."DELIVERY_METHODS" ADD PRIMARY KEY ("DELIVERY_METHOD_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 167 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEW_TABLESPACESPRING2"  ENABLE;
--------------------------------------------------------
--  Constraints for Table USER_STATUSES
--------------------------------------------------------

  ALTER TABLE "DB_USPRING"."USER_STATUSES" ADD PRIMARY KEY ("USER_STATUS_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 167 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEW_TABLESPACESPRING2"  ENABLE;
--------------------------------------------------------
--  Constraints for Table ITEM_TYPES
--------------------------------------------------------

  ALTER TABLE "DB_USPRING"."ITEM_TYPES" ADD PRIMARY KEY ("ITEM_TYPE_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 167 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEW_TABLESPACESPRING2"  ENABLE;
--------------------------------------------------------
--  Constraints for Table ITEMS
--------------------------------------------------------

  ALTER TABLE "DB_USPRING"."ITEMS" ADD PRIMARY KEY ("ITEM_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 167 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEW_TABLESPACESPRING2"  ENABLE;
  ALTER TABLE "DB_USPRING"."ITEMS" ADD CONSTRAINT "CHECK_VEGETARIAN" CHECK (vegetarian in ('y', 'n')) ENABLE;
--------------------------------------------------------
--  Constraints for Table ORDERS
--------------------------------------------------------

  ALTER TABLE "DB_USPRING"."ORDERS" ADD PRIMARY KEY ("ORDER_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 167 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEW_TABLESPACESPRING2"  ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table CARDS
--------------------------------------------------------

  ALTER TABLE "DB_USPRING"."CARDS" ADD FOREIGN KEY ("USER_ID")
	  REFERENCES "DB_USPRING"."USERS" ("USER_ID") ON DELETE CASCADE ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ITEMS
--------------------------------------------------------

  ALTER TABLE "DB_USPRING"."ITEMS" ADD FOREIGN KEY ("ITEM_TYPE_ID")
	  REFERENCES "DB_USPRING"."ITEM_TYPES" ("ITEM_TYPE_ID") ENABLE;
  ALTER TABLE "DB_USPRING"."ITEMS" ADD FOREIGN KEY ("TIME_SLOT_ID")
	  REFERENCES "DB_USPRING"."TIME_SLOTS" ("TIME_SLOT_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table LOCATIONS
--------------------------------------------------------

  ALTER TABLE "DB_USPRING"."LOCATIONS" ADD FOREIGN KEY ("USER_ID")
	  REFERENCES "DB_USPRING"."USERS" ("USER_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ORDERS
--------------------------------------------------------

  ALTER TABLE "DB_USPRING"."ORDERS" ADD FOREIGN KEY ("USER_ID")
	  REFERENCES "DB_USPRING"."USERS" ("USER_ID") ENABLE;
  ALTER TABLE "DB_USPRING"."ORDERS" ADD FOREIGN KEY ("CARD_ID")
	  REFERENCES "DB_USPRING"."CARDS" ("CARD_ID") ENABLE;
  ALTER TABLE "DB_USPRING"."ORDERS" ADD FOREIGN KEY ("DELIVERY_METHOD_ID")
	  REFERENCES "DB_USPRING"."DELIVERY_METHODS" ("DELIVERY_METHOD_ID") ENABLE;
  ALTER TABLE "DB_USPRING"."ORDERS" ADD FOREIGN KEY ("STORE_ID")
	  REFERENCES "DB_USPRING"."STORES" ("STORE_ID") ENABLE;
  ALTER TABLE "DB_USPRING"."ORDERS" ADD FOREIGN KEY ("DELIVERY_STATUS_ID")
	  REFERENCES "DB_USPRING"."DELIVERY_STATUSES" ("DELIVERY_STATUS_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ORDER_ITEMS
--------------------------------------------------------

  ALTER TABLE "DB_USPRING"."ORDER_ITEMS" ADD FOREIGN KEY ("ORDER_ID")
	  REFERENCES "DB_USPRING"."ORDERS" ("ORDER_ID") ENABLE;
  ALTER TABLE "DB_USPRING"."ORDER_ITEMS" ADD FOREIGN KEY ("ITEM_ID")
	  REFERENCES "DB_USPRING"."ITEMS" ("ITEM_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table SPECIALS
--------------------------------------------------------

  ALTER TABLE "DB_USPRING"."SPECIALS" ADD FOREIGN KEY ("ITEM_ID")
	  REFERENCES "DB_USPRING"."ITEMS" ("ITEM_ID") ON DELETE CASCADE ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table STORES
--------------------------------------------------------

  ALTER TABLE "DB_USPRING"."STORES" ADD FOREIGN KEY ("LOCATION_ID")
	  REFERENCES "DB_USPRING"."LOCATIONS" ("LOCATION_ID") ENABLE;
  ALTER TABLE "DB_USPRING"."STORES" ADD FOREIGN KEY ("MANAGER_ID")
	  REFERENCES "DB_USPRING"."USERS" ("USER_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table USERS
--------------------------------------------------------

  ALTER TABLE "DB_USPRING"."USERS" ADD FOREIGN KEY ("USER_STATUS_ID")
	  REFERENCES "DB_USPRING"."USER_STATUSES" ("USER_STATUS_ID") ENABLE;
--------------------------------------------------------
--  DDL for Procedure ADDDELIVERYMETHOD
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "DB_USPRING"."ADDDELIVERYMETHOD" (ID VARCHAR2, Name VARCHAR2)
AS
BEGIN
  INSERT INTO DELIVERY_METHODS (DELIVERY_METHOD_ID, DELIVERY_METHOD)
    VALUES (ID, Name);
END;

/
--------------------------------------------------------
--  DDL for Procedure ADDDELIVERYSTATUS
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "DB_USPRING"."ADDDELIVERYSTATUS" (ID VARCHAR2, Name VARCHAR2)
AS
BEGIN
  INSERT INTO DELIVERY_STATUSES (DELIVERY_STATUS_ID, DELIVERY_STATUS)
    VALUES (ID, Name);
END;

/
--------------------------------------------------------
--  DDL for Procedure ADDORDER
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "DB_USPRING"."ADDORDER" (OrderID VARCHAR2, UserID VARCHAR2, 
Tip NUMBER, TotalPrice NUMBER, PlacedTimestamp NUMBER, DeliveryTimestamp NUMBER,
CardID VARCHAR2, Instructions VARCHAR2, DeliveryMethodID VARCHAR2, 
StoreID VARCHAR2, DeliveryStatusID VARCHAR2)
AS
BEGIN
  INSERT INTO ORDERS (ORDER_ID, USER_ID, TIP, TOTAL_PRICE, PLACED_TIMESTAMP, 
  DELIVERY_TIMESTAMP, CARD_ID, INSTRUCTIONS, DELIVERY_METHOD_ID, STORE_ID, 
  DELIVERY_STATUS_ID)
    VALUES (OrderID, UserID, Tip, TotalPrice, PlacedTimestamp, 
    DeliveryTimestamp, CardID, Instructions, DeliveryMethodID, StoreID, 
    DeliveryStatusID);
END;

/
--------------------------------------------------------
--  DDL for Procedure ADDORDERITEM
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "DB_USPRING"."ADDORDERITEM" (OrderID VARCHAR2, ItemID VARCHAR2)
AS
BEGIN 
  INSERT INTO ORDER_ITEMS (ORDER_ID, ITEM_ID)
  VALUES (OrderID, ItemID);
END;

/
--------------------------------------------------------
--  DDL for Procedure DELETEDELIVERYMETHOD
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "DB_USPRING"."DELETEDELIVERYMETHOD" (ID VARCHAR2)
AS
BEGIN
  DELETE FROM DELIVERY_METHODS 
  WHERE DELIVERY_METHOD_ID = ID;
END;

/
--------------------------------------------------------
--  DDL for Procedure DELETEDELIVERYSTATUS
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "DB_USPRING"."DELETEDELIVERYSTATUS" (ID VARCHAR2)
AS
BEGIN
  DELETE FROM DELIVERY_STATUSES 
  WHERE DELIVERY_STATUS_ID = ID;
END;

/
--------------------------------------------------------
--  DDL for Procedure DELETEORDER
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "DB_USPRING"."DELETEORDER" (OrderID VARCHAR2)
AS
BEGIN
  DELETE FROM ORDERS 
  WHERE ORDER_ID = OrderID;
END;

/
--------------------------------------------------------
--  DDL for Procedure DELETEORDERITEMS
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "DB_USPRING"."DELETEORDERITEMS" (OrderID VARCHAR2)
AS
BEGIN
  DELETE FROM ORDER_ITEMS 
  WHERE ORDER_ID = OrderID;
END;

/
--------------------------------------------------------
--  DDL for Procedure SP_DELETE_USER_BY_ID
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "DB_USPRING"."SP_DELETE_USER_BY_ID" (user_num number)
as 
begin
  Delete from users where user_id = user_num;
end;

/
--------------------------------------------------------
--  DDL for Procedure SP_DELETE_USER_STATUS_BY_ID
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "DB_USPRING"."SP_DELETE_USER_STATUS_BY_ID" (user_status_num number)
as 
begin
  Delete from user_statuses where user_status_id = user_status_num;
end;

/
--------------------------------------------------------
--  DDL for Procedure SP_INSERT_USER
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "DB_USPRING"."SP_INSERT_USER" (user_id varchar, first varchar, last varchar, phone varchar, email varchar, password varchar, user_status_id varchar)
as 
begin
  insert into users
  values(user_id, first, last, phone, email, password, user_status_id);
  
end;

/
--------------------------------------------------------
--  DDL for Procedure SP_INSERT_USER_STATUS
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "DB_USPRING"."SP_INSERT_USER_STATUS" (user_status_id number, user_status varchar)
as 
begin
  insert into user_statuses
  values(user_status_id, user_status);
  
end;

/
--------------------------------------------------------
--  DDL for Procedure SP_UPDATE_USER
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "DB_USPRING"."SP_UPDATE_USER" (user_num varchar, firstN varchar, lastN varchar, pho varchar, eml varchar, psswd varchar, u_s_id varchar)
as 
begin
  update users
  set first=firstN, last=lastN, phone=pho, email=eml, password=psswd, user_status_id=u_s_id
  where user_id=user_num;
end;

/
--------------------------------------------------------
--  DDL for Procedure UPDATEITEM
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "DB_USPRING"."UPDATEITEM" (useID varchar, fname varchar, 
veg char, des varchar, timeSlot varchar,pic varchar, pric number)
as 
begin
  update items
  set name=fname, vegetarian=veg, description = des, time_slot_id = timeSlot, photo=pic, price=pric
  where item_id=useID;
end;

/
--------------------------------------------------------
--  DDL for Procedure UPDATEORDER
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "DB_USPRING"."UPDATEORDER" (OrderID VARCHAR2, UserID VARCHAR2, 
Tip NUMBER, TotalPrice NUMBER, PlacedTimestamp NUMBER, DeliveryTimestamp NUMBER,
CardID VARCHAR2, Instructions VARCHAR2, DeliveryMethodID VARCHAR2, 
StoreID VARCHAR2, DeliveryStatusID VARCHAR2)
AS
BEGIN
  UPDATE ORDERS
  SET USER_ID=UserID, TIP=Tip, TOTAL_PRICE=TotalPrice, 
  PLACED_TIMESTAMP=PlacedTimestamp, DELIVERY_TIMESTAMP=DeliveryTimestamp, 
  CARD_ID=CardID, INSTRUCTIONS=Instructions, 
  DELIVERY_METHOD_ID=DeliveryMethodID, STORE_ID=StoreID, 
  DELIVERY_STATUS_ID=DeliveryStatusID
  WHERE ORDER_ID=OrderID;
END;

/
