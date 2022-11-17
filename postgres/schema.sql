CREATE TABLE users(
    username varchar NOT NULL primary key,
    password varchar NOT NULL,
    enabled boolean NOT NULL
);

CREATE TABLE authorities
(
    username  varchar(50) not null,
    authority varchar(50) not null,
    constraint fk_authorities_users foreign key (username) references users (username)
);

CREATE UNIQUE INDEX ix_auth_username on authorities (username, authority);

INSERT INTO users(username,password,enabled) values ('admin', '$2a$10$33prWtufCb1fjSNbyqwXU./YPYXFfxBWIMb4ER4YGDTWXIDAlCXhC', true);

INSERT INTO authorities(username, authority) values ('admin', 'ROLE_ADMIN');
INSERT INTO authorities(username, authority) values ('admin', 'ROLE_USER');

CREATE TABLE assignment(
    assignment_name varchar PRIMARY KEY,
--    username varchar NOT NULL,
    class_name varchar NOT NULL,
    assignment_weight double precision NOT NULL,
    assignment_score double precision NOT NULL
 --   foreign key (username) references users(username)
);