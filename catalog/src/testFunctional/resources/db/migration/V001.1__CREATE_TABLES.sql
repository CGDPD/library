create table authors
(
    id   bigserial    not null,
    name varchar(255) not null,
    primary key (id)
);
create table book_copies
(
    id              bigserial    not null,
    tracking_status varchar(255) not null,
    user_id         bigint,
    book_id         bigint       not null,
    primary key (id)
);
create table books
(
    id               bigserial    not null,
    title            varchar(255) not null,
    publication_year smallint,
    isbn             varchar(255) not null unique,
    genre            varchar(255) not null,
    author_id        bigint       not null,
    primary key (id)
);
alter table if exists book_copies
    add constraint fk_book_copies_book_id foreign key (book_id) references books;
alter table if exists books
    add constraint fk_book_author_id foreign key (author_id) references authors;
