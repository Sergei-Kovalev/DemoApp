DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    enabled boolean NOT NULL DEFAULT true,
    PRIMARY KEY (username)
);

CREATE TABLE authorities (
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    FOREIGN KEY (username) REFERENCES users(username)
);

INSERT INTO users (username, password, enabled)
VALUES
    ('Sergey', '{noop}Sergey', true),
    ('Georgii', '{noop}Georgii', true);

INSERT INTO authorities(username, authority)
VALUES
    ('Sergey', 'ROLE_USER'),
    ('Georgii', 'ROLE_ADMIN');