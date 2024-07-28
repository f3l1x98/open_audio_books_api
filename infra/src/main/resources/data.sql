INSERT INTO audio_book (id, title, summary, release_date, ongoing, rating, created_date, updated_date)
    VALUES ('7af1db68-353d-49ca-9b48-2be8aa0c2535', 'Test', NULL, NOW(), true, 0, NOW(), NOW());

INSERT INTO episode (id, number, title, summary, release_date, duration, audio_book_id, created_date, updated_date)
    VALUES ('c5798ee4-6778-4c92-ae9c-56b6f732cb5d', 1, 'Episode 1', NULL, NOW(), 10, '7af1db68-353d-49ca-9b48-2be8aa0c2535', NOW(), NOW());

INSERT INTO genre (id, name) VALUES ('fa918bea-7e4c-49c0-a4f3-095b277a16e1', 'TEST GENRE');

INSERT INTO rt_audio_book_genre (audio_book_id, genre_id) VALUES ('7af1db68-353d-49ca-9b48-2be8aa0c2535', 'fa918bea-7e4c-49c0-a4f3-095b277a16e1');

INSERT INTO author (id, first_name, middle_name, last_name) VALUES ('308d1694-b872-470e-8ea6-0d9b9db751eb', 'Mock', NULL, 'Author');

INSERT INTO rt_audio_book_author (audio_book_id, author_id) VALUES ('7af1db68-353d-49ca-9b48-2be8aa0c2535', '308d1694-b872-470e-8ea6-0d9b9db751eb');

INSERT INTO narrator (id, first_name, middle_name, last_name) VALUES ('e627446a-b142-48eb-9dfa-ecba9f37af5b', 'Mock', NULL, 'Author');

INSERT INTO rt_audio_book_narrator (audio_book_id, narrator_id) VALUES ('7af1db68-353d-49ca-9b48-2be8aa0c2535', 'e627446a-b142-48eb-9dfa-ecba9f37af5b');
