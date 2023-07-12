create table users
(
    id               bigserial    not null,
    year_of_birth    smallint     not null,
    first_name       varchar(100) not null,
    last_name        varchar(100) not null,
    gender           varchar(6),
    primary key (id)
);
