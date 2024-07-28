CREATE TABLE IF NOT EXISTS public.audio_book (
    id UUID NOT NULL PRIMARY KEY,
    title VARCHAR(255) NOT NULL UNIQUE,
    summary VARCHAR(255),
    release_date timestamp without time zone NOT NULL,
    ongoing BOOLEAN NOT NULL default true,
    rating INTEGER NOT NULL default 0,
    created_date timestamp without time zone NOT NULL,
    updated_date timestamp without time zone NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_audio_book_title ON audio_book(title);

CREATE TABLE IF NOT EXISTS public.episode (
    id UUID NOT NULL PRIMARY KEY,
    number INTEGER NOT NULL,
    title VARCHAR(255) NOT NULL,
    summary VARCHAR(255),
    release_date timestamp without time zone NOT NULL,
    audio_book_id UUID NOT NULL,
    created_date timestamp without time zone NOT NULL,
    updated_date timestamp without time zone NOT NULL,
    CONSTRAINT uc_number_audio_book_id UNIQUE (number, audio_book_id),
    CONSTRAINT uc_title_audio_book_id UNIQUE (title, audio_book_id),
    CONSTRAINT fk_audio_book FOREIGN KEY (audio_book_id) REFERENCES public.audio_book(id)
);

CREATE TABLE IF NOT EXISTS public.genre (
    id UUID NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE INDEX IF NOT EXISTS idx_genre_name ON genre(name);

CREATE TABLE IF NOT EXISTS public.rt_audio_book_genre (
    audio_book_id UUID NOT NULL,
    genre_id UUID NOT NULL,
    CONSTRAINT pk_audio_book_genre PRIMARY KEY (audio_book_id, genre_id),
    CONSTRAINT fk_audio_book FOREIGN KEY (audio_book_id) REFERENCES public.audio_book(id),
    CONSTRAINT fk_genre FOREIGN KEY (genre_id) REFERENCES public.genre(id)
);

CREATE TABLE IF NOT EXISTS public.author (
    id UUID NOT NULL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    middle_name VARCHAR(255),
    last_name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS public.rt_audio_book_author (
    audio_book_id UUID NOT NULL,
    author_id UUID NOT NULL,
    CONSTRAINT pk_audio_book_author PRIMARY KEY (audio_book_id, author_id),
    CONSTRAINT fk_audio_book__author FOREIGN KEY (audio_book_id) REFERENCES public.audio_book(id),
    CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES public.author(id)
);

CREATE TABLE IF NOT EXISTS public.narrator (
    id UUID NOT NULL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    middle_name VARCHAR(255),
    last_name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS public.rt_audio_book_narrator (
    audio_book_id UUID NOT NULL,
    narrator_id UUID NOT NULL,
    CONSTRAINT pk_audio_book_narrator PRIMARY KEY (audio_book_id, narrator_id),
    CONSTRAINT fk_audio_book__narrator FOREIGN KEY (audio_book_id) REFERENCES public.audio_book(id),
    CONSTRAINT fk_narrator FOREIGN KEY (narrator_id) REFERENCES public.narrator(id)
);
