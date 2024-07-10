package xyz.f3l1x.core.audio_book.exception;

public class UniqueEpisodeViolationException extends Exception {
    public UniqueEpisodeViolationException(boolean numberNotUnique, boolean titleNotUnique) {
        super("Episode not unique for audio book: number unique: " + numberNotUnique + ", title unique: " + titleNotUnique);
    }
}
