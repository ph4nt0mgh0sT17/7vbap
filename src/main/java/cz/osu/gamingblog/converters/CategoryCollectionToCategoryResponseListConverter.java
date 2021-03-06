package cz.osu.gamingblog.converters;

import cz.osu.gamingblog.models.Category;
import cz.osu.gamingblog.responses.CategoryResponse;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.Collection;
import java.util.List;

public class CategoryCollectionToCategoryResponseListConverter implements Converter<Collection<Category>, List<CategoryResponse>> {
    @Override
    public List<CategoryResponse> convert(MappingContext<Collection<Category>, List<CategoryResponse>> context) {
        var categories = context.getSource();

        return categories.stream()
                .map(x -> new CategoryResponse(x.getName(), x.getDescription()))
                .toList();
    }
}
