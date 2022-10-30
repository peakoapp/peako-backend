create table peakodb.peako_user
(
    user_id        bigint                                 not null comment 'An auto-generated table-wise unique id.'
        primary key,
    email          varchar(40)                            not null comment 'A table-wise unique login (email address).',
    email_verified tinyint      default 0                 not null comment 'The verification state of email.',
    password       varchar(100)                           not null comment 'A hashed password by an encryption method.',
    profile_url    varchar(1024)                          not null comment 'The url for the profile picture.',
    bio            varchar(100) default ''                not null comment 'A short bio.',
    first_name     varchar(40)                            not null comment 'The given name.',
    last_name      varchar(40)  default ''                not null comment 'The family name.',
    location       varchar(40)  default ''                not null comment 'The geographical location.',
    provider       varchar(15)  default 'LOCAL'           not null comment 'The authentication provider.',
    non_deleted    tinyint      default 1                 not null comment 'The state of the logical deletion.',
    non_locked     tinyint      default 1                 not null comment 'The state of account lock due to security reasons.',
    enabled        tinyint      default 1                 not null comment 'The state of general account status due to various reasons.',
    version        int          default 0                 not null comment 'The optimistic lock.',
    create_time    datetime     default CURRENT_TIMESTAMP not null comment 'The timestamp at which the record was created.',
    update_time    datetime     default CURRENT_TIMESTAMP not null comment 'The timestamp at which the record was last updated.',
    constraint peako_user_pk
        unique (email)
)
    comment 'Stores users'' profile and account data.';

create table peakodb.peako_friend_request
(
    request_id  bigint                             not null comment 'A table-wise unique id.'
        primary key,
    sender_id   bigint                             not null comment 'The id of the user who sends a friend request to the other.',
    receiver_id bigint                             not null comment 'The id of the user who receives a friend request from the other.',
    approved    tinyint  default 0                 not null comment 'The approval state of the request.',
    denied      tinyint  default 0                 not null comment 'The denial state of the request.',
    expired_at  datetime                           not null comment 'The timestamp at which the friend request will expire.',
    create_time datetime default CURRENT_TIMESTAMP not null comment 'The timestamp at which the friend request was created.',
    constraint peako_friend_request_receiver_id_user_user_id_fk
        foreign key (receiver_id) references peakodb.peako_user (user_id)
            on update cascade on delete cascade,
    constraint peako_friend_request_sender_id_user_user_id_fk
        foreign key (sender_id) references peakodb.peako_user (user_id)
            on update cascade on delete cascade
)
    comment 'Stores friend requests from one user to another.';

create table peakodb.peako_friendship
(
    friendship_id bigint                             not null comment 'A table-wise unique id.'
        primary key,
    user_id       bigint                             not null comment 'The id of the user who''s a friend of the other user''s.',
    friend_id     bigint                             not null comment 'The id of the user who''s a friend of the other user''s.',
    create_time   datetime default CURRENT_TIMESTAMP not null comment 'The timestamp at which the users became friends.',
    constraint peako_friendship_friend_id_user_user_id_fk
        foreign key (friend_id) references peakodb.peako_user (user_id)
            on update cascade on delete cascade,
    constraint peako_friendship_user_user_id_fk
        foreign key (user_id) references peakodb.peako_user (user_id)
            on update cascade on delete cascade
)
    comment 'Stores the friendships between users.';

