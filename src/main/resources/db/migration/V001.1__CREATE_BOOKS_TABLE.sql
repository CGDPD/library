create table books
(
    id             bigserial    not null,
    primary_author varchar(255) not null,
    title          varchar(255) not null,
    primary key (id)
);
