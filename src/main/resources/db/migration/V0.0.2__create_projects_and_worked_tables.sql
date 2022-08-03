CREATE TABLE time_tracker.projects
(
    id         serial PRIMARY KEY,
    name       varchar(255)       NOT NULL,
    is_archive bool default false not null
);

CREATE TABLE time_tracker.sessions
(
    id         bigserial PRIMARY KEY,
    user_id    uuid      not null,
    project_id int       not null,
    start_date timestamp not null,
    end_date   timestamp,
    constraint fk_session_to_user foreign key (user_id) references time_tracker.users (id) on delete cascade,
    constraint fk_session_to_project foreign key (project_id) references time_tracker.projects (id) on delete cascade
);
