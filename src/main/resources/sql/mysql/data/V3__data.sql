INSERT INTO wallet (id, address, balance) VALUES (1, '3J85FLJrjyqz8Dsj5F9pJ9kkQkqJsByt15', 0);
INSERT INTO role (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO role (id, name) VALUES (2, 'ROLE_USER');
INSERT INTO application_user (id, password, username)
VALUES (1, '$2a$16$c8m4JZESnrkb/laxILUjJOQ6SexRA//ltknKvL4C8rFpaOgwhkCsK', 'admin');
INSERT INTO user_share (user_id, share, last_contribution_date)
VALUES (1, 0, utc_timestamp);
INSERT INTO application_user_roles (application_user_id, roles_id) VALUES (1, 1);