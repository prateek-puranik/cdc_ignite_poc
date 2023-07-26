CREATE TABLE COMPANY_3 (COMPANY_CODE INT primary key,COMP_NAME VARCHAR(20),SHARE_PRICE INT);
insert into company_3 values(6,'Bharat Airtel',42);
insert into company_3 values(7,'Tata Motors',109);
insert into company_3 values(8,'HUL',27);
insert into company_3 values(9,'TATA',106);

delete from company_3 where share_price<50;
