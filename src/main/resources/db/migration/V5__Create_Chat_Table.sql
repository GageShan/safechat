create table chats
(
	id varchar(255) not null,
	sendUserId varchar(255) not null,
	receiveUserId varchar(255) not null
);

create unique index chats_id_uindex
	on chats (id);

alter table chats
	add constraint chats_pk
		primary key (id);

