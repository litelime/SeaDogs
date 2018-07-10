ALTER TABLE specials
  ADD (special_id number,
  special_name varchar(4000),
  special_description varchar(4000),
  amount number,
  photo varchar(4000),
  veg varchar(1));
  /
  
  --UPDATE specials SET special_id = 0 WHERE special_id IS NULL
  --ALTER TABLE specials ALTER COLUMN special_id VARCHAR NOT NULL
  --/
  --UPDATE specials SET special_name = 'good meal' WHERE special_id = 0;
  --/