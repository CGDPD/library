create table users
(
    id            bigserial    not null,
    first_name    varchar(100) not null,
    last_name     varchar(100) not null,
    year_of_birth smallint     not null,
    gender        varchar(6) check (gender in ('male', 'female')),
    primary key (id)
);

