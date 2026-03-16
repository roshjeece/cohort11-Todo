package mil.t2com.moda.todo.task;

import jakarta.transaction.Transactional;
import mil.t2com.moda.todo.category.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // full context
@Transactional // don't want to fill database
@AutoConfigureMockMvc // autoconfigure mock
public class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TaskService taskService;

    // Setup test objects
    Task learnTdd;
    Category started;

    @BeforeEach
    void setUp() {
        started = new Category("started");
        learnTdd = new Task( "Learn TDD", "research TDD", false, started);
    }

    @Test
    public void shouldCreateTask() throws Exception {
        String learnTddJson = objectMapper.writeValueAsString(learnTdd);

        MvcResult savedTask = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(learnTddJson))
                .andReturn();
        String expectedType = savedTask.getRequest().getContentType();
        Task expectedTask = objectMapper.readValue(savedTask.getResponse().getContentAsString(), Task.class);

        assertEquals(expectedType, "application/json");
        assertEquals(expectedTask.getTitle(), learnTdd.getTitle()); // Removed "Json" from "learnTdd.getTitle()" to make this work
        assertEquals(expectedTask.getCategory().getLabel(), learnTdd.getCategory().getLabel()); // Removed "Json" from "learnTdd.getTitle()" to make this work
    }

    @Test
    public void shouldGetAllTasks() throws Exception {
        taskService.saveTask(new Task( "First Task", "new task", false, started));
        taskService.saveTask(new Task( "Second Task", "another task", false, started));

        // ASSERT
        mockMvc.perform(get("/api/v1/task"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                //.andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("First Task"))
                .andExpect(jsonPath("$[1].title").value("Second Task"))
                //.andExpect(jsonPath("$[0].category.id").value(1L))
                .andExpect(jsonPath("$[0].category.label").value("started"))
                .andExpect(jsonPath("$[1].category.label").value("started"));
                //.andExpect(jsonPath("$[1].id").value(2L))
    }

    @Test
    public void shouldGetTaskById() throws Exception {
        Task savedTask = taskService.saveTask(new Task("blank task", "no description", false, new Category("failed")));

        mockMvc.perform(get("/api/v1/task/" + savedTask.getId()))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value(""))
                //.andExpect(jsonPath("$.category.id").value(1L))
                .andExpect(jsonPath("$.category.label").value("not started"));
    }
}
