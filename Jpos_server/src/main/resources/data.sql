select * from jpos.end_pos_order;

insert into store (store_name, latitude, longitude, created_date) values ('김치찌개집', 40.5235837, 19.8075358, '1998-01-11 15:00');

insert into store_info (store_id, description, call_num, location) values (1, '설명', '번호', '주소');

insert into user_account (store_id, login_id, login_pw, user_name) values (1, 'test', '$2a$10$eHjUhMueQgO3GxODrxrp7OEipNzWy6BGPEuEOhEyUAKFR7BzMx39i', 'test');
insert into authority (name) values ('ROLE_OWNER');
insert into user_roles (role_id, user_account_id) values (1,1);

insert into user_account (store_id, login_id, login_pw, user_name) values (1, 'aa', '$2a$10$eHjUhMueQgO3GxODrxrp7OEipNzWy6BGPEuEOhEyUAKFR7BzMx39i', 'manager');
insert into authority (name) values ('ROLE_MANAGER');
insert into user_roles (role_id, user_account_id) values (2,2);

insert into category (category_name, store_id) values ('메인', 1);
insert into category (category_name, store_id) values ('사리(찌개용)', 1);
insert into category (category_name, store_id) values ('사이드', 1);
insert into category (category_name, store_id) values ('음료 및 주류', 1);


insert into menu (menu_name, store_id, category_id, price) values ('김치찌개', 1, 1, 10000);
insert into menu (menu_name, store_id, category_id, price) values ('두루치기(소)', 1, 1, 20000);
insert into menu (menu_name, store_id, category_id, price) values ('두루치기(중)', 1, 1, 25000);
insert into menu (menu_name, store_id, category_id, price) values ('두루치기(대)', 1, 1, 30000);
insert into menu (menu_name, store_id, category_id, price) values ('삼겹살', 1, 1, 15000);

insert into menu (menu_name, store_id, category_id, price) values ('라면사리', 1, 2, 1000);
insert into menu (menu_name, store_id, category_id, price) values ('두부사리', 1, 2, 2000);
insert into menu (menu_name, store_id, category_id, price) values ('만두사리', 1, 2, 2000);
insert into menu (menu_name, store_id, category_id, price) values ('당면사리', 1, 2, 2000);
insert into menu (menu_name, store_id, category_id, price) values ('햄사리', 1, 2, 3000);
insert into menu (menu_name, store_id, category_id, price) values ('김치추가', 1, 2, 3000);
insert into menu (menu_name, store_id, category_id, price) values ('고기추가', 1, 2, 7000);

insert into menu (menu_name, store_id, category_id, price) values ('공기밥', 1, 3, 1000);
insert into menu (menu_name, store_id, category_id, price) values ('계란말이', 1, 3, 2000);

insert into menu (menu_name, store_id, category_id, price) values ('음료수', 1, 4, 2000);
insert into menu (menu_name, store_id, category_id, price) values ('소주', 1, 4, 5000);
insert into menu (menu_name, store_id, category_id, price) values ('맥주', 1, 4, 5000);
insert into menu (menu_name, store_id, category_id, price) values ('막걸리', 1, 4, 4000);



insert into seat (seat_name, store_id) values (1, 1);
insert into seat (seat_name, store_id) values (2, 1);
insert into seat (seat_name, store_id) values (3, 1);
insert into seat (seat_name, store_id) values (4, 1);
insert into seat (seat_name, store_id) values (5, 1);
insert into seat (seat_name, store_id) values (6, 1);
insert into seat (seat_name, store_id) values (7, 1);
insert into seat (seat_name, store_id) values (8, 1);
insert into seat (seat_name, store_id) values (9, 1);
insert into seat (seat_name, store_id) values (10, 1);
insert into seat (seat_name, store_id) values (11, 1);
insert into seat (seat_name, store_id) values (12, 1);
insert into seat (seat_name, store_id) values (13, 1);
insert into seat (seat_name, store_id) values (14, 1);
insert into seat (seat_name, store_id) values (15, 1);
insert into seat (seat_name, store_id) values (16, 1);
insert into seat (seat_name, store_id) values (17, 1);
insert into seat (seat_name, store_id) values (18, 1);
insert into seat (seat_name, store_id) values (19, 1);
insert into seat (seat_name, store_id) values (20, 1);
