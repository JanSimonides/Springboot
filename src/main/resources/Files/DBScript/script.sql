create database pokus;
create user admin with encrypted password 'asd';
grant all privileges on database pokus  to admin;
 \c pokus;
create table table_types (
ID_types int primary key not null
);
Insert into table_types (ID_types) values (0),(1);
create table table_states (
ID_states char(1) primary key not null
);
Insert into table_states (ID_states) values ('O'),('M'),('V');
create table property (
	property_id int primary key not null,
	property_name varchar(50) not null,
	property_room varchar(20),
	property_type int not null,
	property_price numeric not null,
	property_in_date date not null,
	property_out_date date,
	property_state char(1),
	foreign key (property_type) references table_types(ID_types),
	foreign key (property_state) references table_states(ID_states)
);
 grant all privileges on table property to admin;
