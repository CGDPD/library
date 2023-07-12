create table users
(
    gender        smallint check (gender between 0 and 1),
    year_of_birth smallint     not null,
    id            bigserial    not null,
    first_name    varchar(100) not null,
    last_name     varchar(100) not null,
    primary key (id)
);
