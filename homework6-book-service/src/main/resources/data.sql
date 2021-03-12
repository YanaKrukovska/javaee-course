DROP TABLE IF EXISTS books;

CREATE TABLE books
(
    id     INT AUTO_INCREMENT PRIMARY KEY,
    title  VARCHAR(50) NOT NULL,
    author VARCHAR(50) NOT NULL,
    isbn   VARCHAR(50) NOT NULL
);


INSERT INTO books (id, title, author, isbn)
VALUES (1, 'Catcher In The Rye', 'J.D. Salinger', '978-966-14-8783-2'),
       (2, 'Martin Eden', 'Jack London', '978-617-07-0777-2'),
       (3, 'Flowers for Algernon', 'Daniel Keyes', '978-617-12-7611-6');