drop index user_id_uindex on user;

create unique index user_id_uindex
	on user (id);

alter table user drop primary key;

alter table user
	add constraint user_pk
		primary key (userId);

alter table user drop key user_id_uindex;

alter table user drop column id;



