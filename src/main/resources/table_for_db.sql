DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS themes;

CREATE TABLE themes (
    id SERIAL NOT NULL,
    name VARCHAR NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE tasks (
    id SERIAL NOT NULL,
    importance INTEGER,
    theme_id INTEGER NOT NULL,
    short_name VARCHAR(40),
    full_description VARCHAR(480),
    start_time TIMESTAMP without TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    end_time TIMESTAMP without TIME ZONE,
    PRIMARY KEY (id),
    FOREIGN KEY (theme_id) REFERENCES themes(id) ON DELETE RESTRICT
);

INSERT INTO themes (name)
VALUES
    ('home'),
    ('work'),
    ('shopping'),
    ('entertainment'),
    ('health'),
    ('social life');

INSERT INTO tasks (importance, theme_id, short_name, full_description, end_time)
VALUES
    (2, 1, 'clean the kitchen', 'Clean windows, gas stove, to throw out the trash, wash the dishes', '2025-01-01 23:00:00'),
    (1, 2, 'invite worker', 'Invite Kovalev Sergey for an internship', '2023-05-01 12:00:00'),
    (3, 3, 'buy new shoes', 'Go to the store and buy new training shoes', '2023-04-20 10:00:00'),
    (4, 4, 'watch new film', 'check new film at cinema, if you find something interesting - watch it!', '2023-04-21 20:00:00');
