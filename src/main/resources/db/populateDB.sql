insert into railway.user (password, password_confirm, username) values ('admin', 'admin', 'admin');
insert into railway.role (name) values ('ROLE_ADMIN');
insert into railway.role (name) values ('ROLE_USER');
insert into railway.user_role (user_id, role_id) values (1, 1);