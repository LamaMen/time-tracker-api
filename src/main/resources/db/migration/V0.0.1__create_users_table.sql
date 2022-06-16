CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE SCHEMA IF NOT EXISTS time_tracker;

CREATE TABLE time_tracker.users
(
    id       uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    name     varchar(255) NOT NULL,
    surname  varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    role     varchar(60)  NOT NULL
);

INSERT INTO TIME_TRACKER.users (name, surname, password, role)
VALUES ('Admin', 'Admin', 'protected_password', 'Admin');