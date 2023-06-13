alter table books add constraint fk_author_id foreign key (author_id) references author (id);
