insert into railway.user 
(password, password_confirm, username, birthday, country, create_time, credential_number, credential_type, cust_type, email, gender, nickname, telephone) 
values 
('admin', 'admin', 'admin', '1986-07-11', 0, now(), '520402198607118228', 0, 0, '759876936@qq.com',    0, 'admin', '4008199199' ),
('test',  'test',  'test',  '1982-10-02', 0, now(), '422828198210021528', 0, 0, '759876936@gmail.com', 0, 'test',  '13476074876');

insert into railway.role (name) values ('ROLE_ADMIN');
insert into railway.role (name) values ('ROLE_USER');

insert into railway.user_role (user_id, role_id) values (1, 1);
insert into railway.user_role (user_id, role_id) values (2, 2);