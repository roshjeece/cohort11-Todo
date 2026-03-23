package mil.t2com.moda.todo.task;

import mil.t2com.moda.todo.category.Category;
import mil.t2com.moda.todo.category.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    TaskService taskService;

    Task learnMock;
    Task learnTdd;
    Category started;
    Category finished;

    List<Task> tasks = new ArrayList<>();

    @BeforeEach
    void setUp() {
        started = new Category("started");
        finished = new Category("finished");
        learnMock = new Task("Learn about MOCKS", "Learn about Inject mocks", false, started);
        learnMock.setId(1L);
        learnTdd = new Task("Learn TDD", "I learned TDD", true, finished);
        learnTdd.setId(2L);

    }

    @Test
    void shouldSaveNewTask() {
        // Arrange

        // Act
        when(taskRepository.save(learnMock)).thenReturn(learnMock);

        Task result = taskService.saveTask(learnMock);

        // Assert
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Learn about MOCKS");
        assertThat(result.getCategory().getLabel()).isEqualTo("started");

        verify(taskRepository, only()).save(learnMock);
        verify(categoryService, only()).findCategoryByLabel("started");
    }

    @Test
    void shouldSaveNewTaskWithNewCategory() {
        // Act
        when(categoryService.findCategoryByLabel(started.getLabel())).thenReturn(Optional.of(started));
        when(taskRepository.save(learnMock)).thenReturn(learnMock);

        Task result = taskService.saveTask(learnMock);
    }

    @Test
    void shouldGetAllTasks() {
        // Act
        tasks.addAll(List.of(learnMock, learnTdd));
        when(taskRepository.findAll()).thenReturn(tasks);

        // Assert
        List<Task> results = taskService.findAllTasks();

        verify(taskRepository, only()).findAll();
        assertThat(results).isEqualTo(tasks);
    }

    @Test
    void shouldFindTaskById() {
        // Act
        when(taskRepository.findById(1L)).thenReturn(Optional.of(learnMock));

        Task result = taskService.findTaskById(1L);

        verify(taskRepository, only()).findById(1L);
        assertThat(result).isEqualTo(learnMock);
    }
}