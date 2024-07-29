package xyz.f3l1x.open_audio_books_api.app.audio_book.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.f3l1x.open_audio_books_api.app.author.dto.AuthorListDto;
import xyz.f3l1x.open_audio_books_api.app.narrator.dto.NarratorListDto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class AudioBookListDto {
    private UUID id;
    private String title;

    private List<AuthorListDto> authors;
    private List<NarratorListDto> narrators;
}
