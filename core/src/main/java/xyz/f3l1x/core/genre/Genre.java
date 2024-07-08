package xyz.f3l1x.core.genre;

import lombok.*;
import xyz.f3l1x.core.shared.BaseModel;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Genre extends BaseModel {
    private String name;

    public static Genre create(String name) {
        return new Genre(name);
    }
}
