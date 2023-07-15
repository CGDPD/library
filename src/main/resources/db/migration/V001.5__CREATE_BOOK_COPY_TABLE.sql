create table book_copy
(
    book_id         bigint       not null,
    id              bigserial    not null,
    user_id         bigint,
    tracking_status varchar(255) not null check (tracking_status in
                                                 ('BEING_PROCESSED', 'AVAILABLE', 'ON_HOLD',
                                                  'CHECKED_OUT', 'LOST', 'RETIRED',
                                                  'REFERENCE')),
    primary key (id)
);
alter table book_copy
    add constraint fk_book_id foreign key (book_id) references books (id);
alter table book_copy
    add constraint fk_user_id foreign key (user_id) references users (id);
