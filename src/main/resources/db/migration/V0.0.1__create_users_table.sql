create schema time_tracker;

CREATE TABLE TIME_TRACKER.USERS
(
    id       uuid PRIMARY KEY,
    name     varchar(255) NOT NULL,
    surname  varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    role     varchar(60)  NOT NULL
);
