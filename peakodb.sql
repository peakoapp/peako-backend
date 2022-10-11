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

