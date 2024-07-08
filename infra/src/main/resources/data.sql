INSERT INTO audio_book (title, summary, release_date, ongoing, rating, created_date, updated_date)
    VALUES ('Test', NULL, NOW(), true, 0, NOW(), NOW());

INSERT INTO episode (number, title, summary, release_date, audio_book_id, created_date, updated_date)
    VALUES (1, 'Episode 1', NULL, NOW(), 1, NOW(), NOW());

INSERT INTO genre (name) VALUES ('TEST GENRE');

INSERT INTO rt_audio_book_genre (audio_book_id, genre_id) VALUES (1, 1);