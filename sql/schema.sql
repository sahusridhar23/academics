CREATE DATABASE complaints;

\c complaints

CREATE TABLE IF NOT EXISTS complaints (
    id          SERIAL        PRIMARY KEY,
    name        VARCHAR(100)  NOT NULL,
    category    VARCHAR(50)   NOT NULL,
    title       VARCHAR(120)  NOT NULL,
    description VARCHAR(1000) NOT NULL,
    status      VARCHAR(20)   NOT NULL DEFAULT 'Pending',
    created_at  TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP
);
