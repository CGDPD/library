alter table users
    drop constraint if exists users_gender_check;

alter table users
    add constraint users_gender_check check (gender in ('MALE', 'FEMALE'));

update users
set gender = upper(gender);
