delete from reservation;
delete from book;
delete from genre;

insert into genre(id, name) values
(1, 'g1'),
(2, 'g2');

insert into book(id, content, title) values
(1, 'content1', 'title1'),
(2, 'content2', 'title2');

insert into reservation(id, user, book_id, reserved) values
(1,  'user1', 1, 1),
(2,  'user1', 1, 0),
(3,  'user2', 1, 1);