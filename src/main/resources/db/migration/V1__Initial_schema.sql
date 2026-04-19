CREATE TABLE video (
    id                  BIGSERIAL PRIMARY KEY NOT NULL,
    version             integer NOT NULL,
    title               varchar(255) NOT NULL,
    date                varchar(255) NOT NULL,
    start               varchar(255) NOT NULL,
    duration            varchar(255) NOT NULL,
    link                varchar(255) NOT NULL,
    created_date        timestamp NOT NULL,
    last_modified_date  timestamp NOT NULL
);