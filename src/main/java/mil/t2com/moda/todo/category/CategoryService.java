package mil.t2com.moda.todo.category;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Optional<Category> findCategoryByLabel(String label) {
        return categoryRepository.findCategoryByLabel(label);
    }

    public Category createCategoryIfNotExist(String label) {
        Optional<Category> queryCategory = categoryRepository.findCategoryByLabel(label);

        if(queryCategory.isEmpty()) { return categoryRepository.save(new Category(label));}

        return queryCategory.get();
    }

    // ADD with Tests for: GetById, Put, Delete

}
