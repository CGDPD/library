create table author
(
    id   bigserial    not null,
    name varchar(100) not null,
    primary key (id)
);
create table books
(
    publication_year date,
    author_id        bigint       not null,
    id               bigserial    not null,
    genre            varchar(100) not null,
    title            varchar(100) not null,
    isbn             varchar(255),
    primary key (id)
);

