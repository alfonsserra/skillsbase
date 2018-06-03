INSERT INTO users (id, user_surname, user_name, user_login, user_password, user_role) VALUES (1, 'Chandler', 'Sharat', 'user','$2a$04$I9Q2sDc4QGGg5WNTLmsz0.fvGv3OjoZyj81PrSFyGOqMphqfS2qKu', 'USER');
INSERT INTO users (id, user_surname, user_name, user_login, user_password, user_role) VALUES (2, 'Reinhold', 'Mark', 'admin','$2a$04$I9Q2sDc4QGGg5WNTLmsz0.fvGv3OjoZyj81PrSFyGOqMphqfS2qKu', 'ADMIN');
INSERT INTO users (id, user_surname, user_name, user_login, user_password, user_role) VALUES (3, 'Systelab', 'Systelab', 'Systelab','$2a$10$9wXu9hshOrtZ7RopythgF.XP93XbKtISBJMpz4PFHG4zv6QjTGBzq', 'ADMIN');

INSERT INTO skill (id, text, type, comments, parent_skill) VALUES(1,'Top','category','',null);
INSERT INTO skill (id, text, type, comments, parent_skill) VALUES(2,'General','category','',1);
INSERT INTO skill (id, text, type, comments, parent_skill) VALUES(3,'Development','category','',1);
INSERT INTO skill (id, text, type, comments, parent_skill) VALUES(4,'.Net','skill','',2);
INSERT INTO skill (id, text, type, comments, parent_skill) VALUES(5,'Java','skill','',2);
INSERT INTO skill (id, text, type, comments, parent_skill) VALUES(6,'C++','skill','',2);
INSERT INTO skill (id, text, type, comments, parent_skill) VALUES(7,'Big Data','category','',1);
