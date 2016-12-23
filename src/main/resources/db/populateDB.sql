insert into railway.user 
(password, password_confirm, username, birthday, country, create_time, credential_number, credential_type, cust_type, email, gender, nickname, telephone) 
values 
('admin', 'admin', 'admin', '1986-07-11', 0, now(), '520402198607118228', 0, 0, '759876936@qq.com',    0, 'admin', '4008199199' ),
('test',  'test',  'test',  '1982-10-02', 0, now(), '422828198210021528', 0, 0, '759876936@gmail.com', 0, 'test',  '13476074876');

insert into railway.role (name) values ('ROLE_ADMIN');
insert into railway.role (name) values ('ROLE_USER');

insert into railway.user_role (user_id, role_id) values (1, 1);
insert into railway.user_role (user_id, role_id) values (2, 2);

INSERT INTO railway.ticket 
(train_no, station_name, train_tag, start_time, dept_time, stat_order, price_first_class, ticket_first_class, price_second_class, ticket_second_class, price_stand, ticket_stand) 
VALUES 
('D5242','武昌','20160807D5242',str_to_date('2016-08-07 09:10', '%Y-%m-%d %H:%i'),null,0,0,300,0,1000,0,500),
('D5242','随州','20160807D5242',str_to_date('2016-08-07 10:36', '%Y-%m-%d %H:%i'),str_to_date('2016-08-07 10:34', '%Y-%m-%d %H:%i'),1,62.5,300,51.5,1000,51.5,500),
('D5242','枣阳','20160807D5242',str_to_date('2016-08-07 11:13', '%Y-%m-%d %H:%i'),str_to_date('2016-08-07 10:11', '%Y-%m-%d %H:%i'),2,89.5,300,74.5,1000,74.5,500),
('D5242','襄阳','20160807D5242',null,str_to_date('2016-08-07 11:46', '%Y-%m-%d %H:%i'),3,115.5,300,96.5,1000,96.5,500),
('D5232','武昌','20160807D5232',str_to_date('2016-08-07 17:00', '%Y-%m-%d %H:%i'),null,0,0,300,0,1000,0,500),
('D5232','云梦','20160807D5232',str_to_date('2016-08-07 17:50', '%Y-%m-%d %H:%i'),str_to_date('2016-08-07 17:48', '%Y-%m-%d %H:%i'),1,31.5,300,26.5,1000,26.5,500),
('D5232','随州','20160807D5232',str_to_date('2016-08-07 18:30', '%Y-%m-%d %H:%i'),str_to_date('2016-08-07 18:28', '%Y-%m-%d %H:%i'),2,62.5,300,51.5,1000,51.5,500),
('D5232','襄阳','20160807D5232',null,str_to_date('2016-08-07 19:36', '%Y-%m-%d %H:%i'),3,115.5,300,96.5,1000,96.5,500);

INSERT INTO railway.train
(train_no, station_name, start_time, dept_time, stat_order, price_first_class, ticket_first_class, price_second_class, ticket_second_class, price_stand, ticket_stand) 
VALUES 
('D5242','武昌','09:10',null,0,0,300,0,1000,0,500),
('D5242','随州','10:36','10:34',1,62.5,300,51.5,1000,51.5,500),
('D5242','枣阳','11:13','10:11',2,89.5,300,74.5,1000,74.5,500),
('D5242','襄阳',null,'11:46',3,115.5,300,96.5,1000,96.5,500),
('D5232','武昌','17:00',null,0,0,300,0,1000,0,500),
('D5232','云梦','17:50','17:48',1,31.5,300,26.5,1000,26.5,500),
('D5232','随州','18:30','18:28',2,62.5,300,51.5,1000,51.5,500),
('D5232','襄阳',null,'19:36',3,115.5,300,96.5,1000,96.5,500);

INSERT INTO 
railway.contacts (user_id, username, gender, country, credential_type, credential_number, telephone, cust_type)
SELECT id, username, 0 AS gender, 0 AS country, credential_type, credential_number, telephone, cust_type FROM railway.user;