INSERT INTO users (id,username, email, PASSWORD) VALUES
(1, 'OlegVynnyk', 'olegvynnyk@gmail.com', '$2a$10$vx65JDXg5cH.40IwIh01eOmShF6xTuZ8ujE48kzWBdnXaRoccl69m'),
(2, 'JustinBiever', 'justin@gmail.com', '$2a$10$vx65JDXg5cH.40IwIh01eOmShF6xTuZ8ujE48kzWBdnXaRoccl69m'),
(3, 'BarackObama', 'barack@gmail.com', '$2a$10$vx65JDXg5cH.40IwIh01eOmShF6xTuZ8ujE48kzWBdnXaRoccl69m');

INSERT INTO user_roles(id, name) VALUES
(1, 'ROLE_USER'), (2, 'ROLE_ADMIN');

INSERT INTO users_roles(users_id, roles_id) VALUES
(1, 1),
(2, 1),
(3, 2);

INSERT INTO books (id, title, author, isbn)
VALUES (1, 'Catcher In The Rye', 'J.D. Salinger', '978-966-14-8783-2'),
       (2, 'Martin Eden', 'Jack London', '978-617-07-0777-2'),
       (3, 'Flowers for Algernon', 'Daniel Keyes', '978-617-12-7611-6');

INSERT INTO favourite_books(user_id, book_id) VALUES
(1, 1),
(1, 2);
