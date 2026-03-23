package mil.t2com.moda.todo.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    CategoryService categoryService;

    Category newCategory;

    @Mock
    CategoryService underMock;

    // Start using when refactoring
    @BeforeEach
    void setUp() {
        newCategory = new Category("Delayed");  // meaningful name for the save test
        newCategory.setId(1L);
    }

    @Test
    void shouldSaveNewCategory() {
        when(categoryRepository.save(newCategory)).thenReturn(newCategory);
        Category result = categoryService.saveCategory(newCategory);
        assertThat(result.getLabel()).isEqualTo("Delayed");
        verify(categoryRepository, only()).save(newCategory);
    }

    @Test
    void shouldCreateCategoryWhenNotFound() {
        // fully self-contained — no shared field needed
        Category created = new Category("not exists");
        when(categoryRepository.findCategoryByLabel("not exists")).thenReturn(Optional.empty());
        when(categoryRepository.save(any(Category.class))).thenReturn(created);

        Category result = categoryService.createCategoryIfNotExist("not exists");

        assertThat(result.getLabel()).isEqualTo("not exists");
        verify(categoryRepository).findCategoryByLabel("not exists");
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void shouldReturnExistingCategoryWhenFound() {
        // the OTHER branch — category already exists, don't save
        Category existing = new Category("already exists");
        when(categoryRepository.findCategoryByLabel("already exists")).thenReturn(Optional.of(existing));

        Category result = categoryService.createCategoryIfNotExist("already exists");

        assertThat(result.getLabel()).isEqualTo("already exists");
        verify(categoryRepository).findCategoryByLabel("already exists");
        verify(categoryRepository, never()).save(any(Category.class));  // key assertion
    }

}