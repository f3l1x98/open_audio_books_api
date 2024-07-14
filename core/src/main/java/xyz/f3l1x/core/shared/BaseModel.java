package xyz.f3l1x.core.shared;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public abstract class BaseModel {
    private UUID id;

    protected BaseModel() {
        this.id = UUID.randomUUID();
    }
}
