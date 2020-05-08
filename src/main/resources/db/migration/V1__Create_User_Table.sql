create table user
(
	id varchar(64) not null,
	userId varchar(255) not null,
	username varchar(255) not null,
	password varchar(255) not null,
	avatarUrl varchar(255) not null
);

create unique index user_id_uindex
	on user (id);

create unique index user_userId_uindex
	on user (userId);

alter table user
	add constraint user_pk
		primary key (id);

