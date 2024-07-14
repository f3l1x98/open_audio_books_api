package xyz.f3l1x.open_audio_books_api.app.mapper;

import org.modelmapper.AbstractConverter;
import xyz.f3l1x.core.shared.BaseModel;

import java.util.Collection;
import java.util.UUID;

public class BaseModelCollectionToIdCollectionConverter extends AbstractConverter<Collection<BaseModel>, Collection<UUID>> {
    @Override
    protected Collection<UUID> convert(Collection<BaseModel> baseModels) {
        return baseModels.stream().map(BaseModel::getId).toList();
    }
}
