package cz.osu.gamingblog.services.interfaces;

import cz.osu.gamingblog.requests.CreateCategoryRequest;
import cz.osu.gamingblog.responses.CategoryResponse;

import java.util.List;

public interface ICategoryService {
    /**
     * Retrievese all categories.
     *
     * @return The {@link List} of {@link CategoryResponse} objects.
     */
    List<CategoryResponse> retrieveAll();

    /**
     * Creates the category via the {@link CreateCategoryRequest} object.
     *
     * @param createCategoryRequest The object containg the information about new category.
     */
    void create(CreateCategoryRequest createCategoryRequest);
}
