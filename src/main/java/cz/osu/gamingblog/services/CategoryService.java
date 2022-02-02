package cz.osu.gamingblog.services;

import cz.osu.gamingblog.models.Category;
import cz.osu.gamingblog.repositories.ICategoryRepository;
import cz.osu.gamingblog.requests.CreateCategoryRequest;
import cz.osu.gamingblog.responses.CategoryResponse;
import cz.osu.gamingblog.services.interfaces.ICategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    private final ICategoryRepository _categoryRepository;
    private final ModelMapper _modelMapper;

    public CategoryService(ICategoryRepository categoryRepository, ModelMapper modelMapper) {
        _categoryRepository = categoryRepository;
        _modelMapper = modelMapper;
    }

    @Override
    public List<CategoryResponse> retrieveAll() {
        var categories = _categoryRepository.findAll();

        return categories.stream()
                .map(x -> _modelMapper.map(x, CategoryResponse.class))
                .toList();
    }

    @Override
    public void create(CreateCategoryRequest createCategoryRequest) {
        var category = _modelMapper.map(createCategoryRequest, Category.class);

        try {
            _categoryRepository.save(category);
        } catch (Exception ex) {
            throw new IllegalStateException("Could not create category.", ex);
        }
    }
}
