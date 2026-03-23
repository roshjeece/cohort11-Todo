package mil.t2com.moda.todo.task;

import mil.t2com.moda.todo.category.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    TaskService taskService;

    @Captor
    ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
    ArgumentCaptor<List<Task>> captors = ArgumentCaptor.forClass(List.class);

    String enablement = "enablement";
    Task learnHttpMethods;
    Task learnCaptor;
    Category enableCategory = new Category(enablement);
    Category studyCategory = new Category("study");

    List<Task> tasks = new ArrayList<>();

    @BeforeEach
    void setup() {
        // Arrange
        learnHttpMethods = new Task(
                "Learn about testing HTTP request/response",
                "Learn how to use WebMvcTest",
                false,
                enableCategory);
        learnHttpMethods.setId(1L);
        learnCaptor = new Task(
                "Learn Captor",
                "Learn how to use captor",
                false,
                studyCategory);

        when(taskService.saveTask(any(Task.class))).thenReturn(learnHttpMethods);
    }

    @Test
    void shouldSaveNewTask() throws Exception {
        // Act
        mockMvc.perform(post("/api/v1/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(learnHttpMethods)))
                // result matchers
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(matchesPattern("Learn about.*request/response")))
                .andExpect(jsonPath("$.description").value(containsString("Learn how to")))
                .andExpect(jsonPath("$.category.label").value("enablement"))
                .andDo(print()
                );

        // Assert
        verify(taskService, times(1)).saveTask(any(Task.class));
    }

    @Test
    void shouldSaveNewTaskUsingCaptor() throws Exception {
        // Act
        mockMvc.perform(post("/api/v1/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(learnHttpMethods)))
                // result matchers
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(matchesPattern("Learn about.*request/response")))
                .andExpect(jsonPath("$.description").value(containsString("Learn how to")))
                .andExpect(jsonPath("$.category.label").value("enablement"))
                .andDo(print()
                );

        // Assert
        verify(taskService, only()).saveTask(captor.capture());
        assertThat(captor.getValue()).usingRecursiveComparison().isEqualTo(learnHttpMethods);

        verify(taskService, only()).saveTask(any(Task.class));
    }

    @Test
    void shouldFindAllTasks() throws Exception {
        tasks.addAll(List.of(learnHttpMethods, learnHttpMethods));

        when(taskService.findAllTasks()).thenReturn(tasks);

        mockMvc.perform(get("/api/v1/task"))
                // result matchers
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(taskService, times(1)).findAllTasks();
        assertThat(captors.getAllValues()).usingRecursiveComparison().isEqualTo(tasks);
        verify(taskService, only()).findAllTasks();
    }

    @Test
    void shouldSaveAllTasks() {
//        ArgumentCaptor<List<Task>> captorAll = ArgumentCaptor.forClass(List.class);
//
//        taskService.saveAllTasks(captorAll.capture()); // method under test
//
//        verify(taskService).findAllTasks(captorAll.capture());
//
//        List<Task> result = captorAll.getAllValues();
//
//        assertThat(result)
//                .hasSize(2)
//                .extracting(Task::getId)
//                .containsExactly(1L, 2L);
    }

    @Test
    void shouldFindTaskById() throws Exception {
        when(taskService.findTaskById(1L)).thenReturn(learnHttpMethods);

        String taskJson = objectMapper.writeValueAsString(learnHttpMethods);

        mockMvc.perform(get("/api/v1/task/1"))
                // result matchers
                .andExpect(status().isOk())
                .andExpect(content().json(taskJson));

//        verify(taskService).findAllTasks(captors.capture());
//        verify(taskService).findAllTasks(captor.capture());
//        assertThat(captor.getAllValues()).usingRecursiveComparison().isEqualTo(tasks);
        verify(taskService, only()).findTaskById(1L);

    }


}