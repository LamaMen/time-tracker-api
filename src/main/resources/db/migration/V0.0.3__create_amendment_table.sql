CREATE TABLE time_tracker.amendment
(
    id             bigserial PRIMARY KEY,
    user_id        uuid              not null,
    project_id     int               not null,
    amendment_date date              not null,
    is_positive    bool default true not null,
    hours          int               not null,
    minutes        int               not null,

    constraint fk_amendment_to_user foreign key (user_id) references time_tracker.users (id) on delete cascade,
    constraint fk_amendment_to_project foreign key (project_id) references time_tracker.projects (id) on delete cascade
);