create table book_copies
(
    id              bigserial   not null,
    book_id         bigint      not null,
    tracking_status varchar(32) not null check (tracking_status in
                                                ('BEING_PROCESSED', 'AVAILABLE', 'ON_HOLD',
                                                 'CHECKED_OUT', 'LOST', 'RETIRED', 'REFERENCE')),
    user_id         bigint,
    primary key (id)
);
alter table book_copies
    add constraint fk_book_copies_book_id foreign key (book_id) references books (id);
alter table book_copies
    add constraint fk_book_copies_user_id foreign key (user_id) references users (id);
