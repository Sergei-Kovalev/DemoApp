DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
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
    ('Sergey', '{bcrypt}$2a$12$dzmhxdtZCfqZZ.qY1EWWQu8urR1UeCiLIVLVdJp99oxlX5cY9RyHS', true),
    ('Georgii', '{bcrypt}$2a$12$eO/eJfe/LOQslClWmShl/.F6hjMlEPhK9K0vOD1o.mnAA0tAqHu8G', true);

INSERT INTO authorities(username, authority)
VALUES
    ('Sergey', 'ROLE_USER'),
    ('Georgii', 'ROLE_ADMIN');