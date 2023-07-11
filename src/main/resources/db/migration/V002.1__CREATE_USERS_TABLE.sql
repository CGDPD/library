create table users
(
    id               bigserial    not null,
    year_of_birth    integer      not null,
    first_name       varchar(100) not null,
    last_name        varchar(100) not null,
    gender           varchar(100),
    primary key (id)
);
