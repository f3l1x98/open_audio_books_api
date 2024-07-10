package xyz.f3l1x.open_audio_books_api.app.mapper;

import org.modelmapper.AbstractConverter;
import xyz.f3l1x.core.shared.BaseModel;

import java.util.Collection;

public class BaseModelCollectionToIdCollectionConverter extends AbstractConverter<Collection<BaseModel>, Collection<Long>> {
    @Override
    protected Collection<Long> convert(Collection<BaseModel> baseModels) {
        return baseModels.stream().map(BaseModel::getId).toList();
    }
}
