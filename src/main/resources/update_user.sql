ALTER TABLE users ALTER COLUMN password TYPE VARCHAR(100);

UPDATE users SET enabled = false WHERE username = 'Sergey';

UPDATE users SET password = '{bcrypt}$2a$12$dzmhxdtZCfqZZ.qY1EWWQu8urR1UeCiLIVLVdJp99oxlX5cY9RyHS' WHERE username = 'Sergey';
UPDATE users SET password = '{bcrypt}$2a$12$eO/eJfe/LOQslClWmShl/.F6hjMlEPhK9K0vOD1o.mnAA0tAqHu8G' WHERE username = 'Georgii';

