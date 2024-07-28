package xyz.f3l1x.open_audio_books_api.app.narrator.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class NarratorDto {
    private UUID id;
    private String firstName;
    private String middleName;
    private String lastName;
}