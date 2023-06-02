create table author
(
    id   bigserial not null,
    name varchar(255),
    primary key (id)
);
create table books
(
    publication_year date,
    id               bigserial    not null,
    author           varchar(100) not null,
    genre            varchar(100) not null,
    title            varchar(100) not null,
    isbn             varchar(255),
    primary key (id)
);
