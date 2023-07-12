alter table author
    rename to authors;
alter table books
    alter column isbn set not null;
