package mil.t2com.moda.todo.task;

import mil.t2com.moda.todo.category.Category;
import mil.t2com.moda.todo.category.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final CategoryService categoryService;


    public TaskService(TaskRepository taskRepository, CategoryService categoryService) {
        this.taskRepository = taskRepository;
        this.categoryService = categoryService;
    }

    public Task saveTask(Task task) {

        Optional<Category> existsingCategory = categoryService.findCategoryByLabel(task.getCategory().getLabel());
        if(existsingCategory.isPresent()) {

        }

        return taskRepository.save(task);
    }

    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    public Task findTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow();
    }

    public List<Task> saveAllTasks(List<Task> tasks) {
        return taskRepository.saveAll(tasks);
    }

    // ADD with Tests for:  Put, Delete
}
