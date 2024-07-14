INSERT INTO audio_book (id, title, summary, release_date, ongoing, rating, created_date, updated_date)
    VALUES ('7af1db68-353d-49ca-9b48-2be8aa0c2535', 'Test', NULL, NOW(), true, 0, NOW(), NOW());

INSERT INTO episode (id, number, title, summary, release_date, audio_book_id, created_date, updated_date)
    VALUES ('c5798ee4-6778-4c92-ae9c-56b6f732cb5d', 1, 'Episode 1', NULL, NOW(), '7af1db68-353d-49ca-9b48-2be8aa0c2535', NOW(), NOW());

INSERT INTO genre (id, name) VALUES ('fa918bea-7e4c-49c0-a4f3-095b277a16e1', 'TEST GENRE');

INSERT INTO rt_audio_book_genre (audio_book_id, genre_id) VALUES ('7af1db68-353d-49ca-9b48-2be8aa0c2535', 'fa918bea-7e4c-49c0-a4f3-095b277a16e1');