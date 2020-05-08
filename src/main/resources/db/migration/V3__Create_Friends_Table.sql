create table friends
(
	id varchar(266) not null,
	myUserId varchar(255) not null,
	friendUserId int not null
);

create unique index friends_id_uindex
	on friends (id);

alter table friends
	add constraint friends_pk
		primary key (id);

