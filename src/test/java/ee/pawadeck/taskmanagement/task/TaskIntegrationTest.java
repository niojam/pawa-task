package ee.pawadeck.taskmanagement.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.pawadeck.taskmanagement.EnableTestcontainers;
import ee.pawadeck.taskmanagement.comment.model.CommentModel;
import ee.pawadeck.taskmanagement.comment.repository.CommentRepository;
import ee.pawadeck.taskmanagement.task.dto.TaskCreateRequest;
import ee.pawadeck.taskmanagement.task.dto.TaskUpdateRequest;
import ee.pawadeck.taskmanagement.task.model.Priority;
import ee.pawadeck.taskmanagement.task.model.TaskModel;
import ee.pawadeck.taskmanagement.task.repository.TaskRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:db/task/test-data.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:db/task/delete-data.sql"),
})
@AutoConfigureMockMvc(addFilters = false)
@EnableTestcontainers
public class TaskIntegrationTest {


    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuditorAware<UUID> auditorAware;

    @MockBean
    private TaskEventProducer taskEventProducer;


    @Autowired
    private TaskRepository taskRepository;


    @Autowired
    private CommentRepository commentRepository;


    @Test
    public void shouldCreateTask() throws Exception {
        Optional<UUID> createdBy = Optional.of(UUID.fromString("be288274-1773-4181-a234-e8349b6b07ab"));
        when(auditorAware.getCurrentAuditor()).thenReturn(createdBy);

        Instant deadline = Instant.now().plus(10, ChronoUnit.DAYS);
        String title = "title";
        String comment = "comment";
        String description = "description";
        TaskCreateRequest createRequest = new TaskCreateRequest()
                .setTitle(title)
                .setComment(comment)
                .setDescription(description)
                .setDeadline(deadline)
                .setPriority(Priority.LOW);

        mvc.perform(post("/api/v1/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title", Matchers.is(title)))
                .andExpect(jsonPath("$.description", Matchers.is(description)))
                .andExpect(jsonPath("$.priority", Matchers.is(Priority.LOW.name())))
                .andExpect(jsonPath("$.createdBy", Matchers.is(createdBy.get().toString())))
                .andExpect(jsonPath("$.lastEditBy", Matchers.is(createdBy.get().toString())));
    }

    @Test
    public void shouldDeleteTaskAndRelatedComments() throws Exception {
        Optional<UUID> createdBy = Optional.of(UUID.fromString("be288274-1773-4181-a234-e8349b6b07ab"));
        when(auditorAware.getCurrentAuditor()).thenReturn(createdBy);


        Long taskId = 1L;
        mvc.perform(delete("/api/v1/task/"  + taskId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        Optional<TaskModel> saved = taskRepository.findById(taskId);
        assertThat(saved.isEmpty()).isTrue();

        List<CommentModel> comments = commentRepository.findAllByTaskId(taskId);
        assertThat(comments).isEmpty();

    }


    @Test
    public void shouldUpdateTask() throws Exception {
        Optional<UUID> userId = Optional.of(UUID.fromString("be288274-1773-4181-a234-e8349b6b07ab"));
        when(auditorAware.getCurrentAuditor()).thenReturn(userId);

        Instant deadline = Instant.now().plus(10, ChronoUnit.DAYS);
        String title = "newTitle";
        String description = "newdescription";
        TaskUpdateRequest updateRequest = new TaskUpdateRequest()
                .setTitle(title)
                .setDescription(description)
                .setDeadline(deadline)
                .setPriority(Priority.CRITICAL);

        String taskId = "1";
        mvc.perform(put("/api/v1/task/"  + taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk());

        Optional<TaskModel> saved = taskRepository.findById(1L);
        assertThat(saved.isPresent()).isTrue();
        TaskModel taskModel = saved.get();

        assertThat(taskModel.getTitle()).isEqualTo(title);
        assertThat(taskModel.getDescription()).isEqualTo(description);
        assertThat(taskModel.getPriority()).isEqualTo(Priority.CRITICAL);
        assertThat(taskModel.getModifiedBy()).isEqualTo(userId.get());
    }

    @Test
    public void shouldValidateDeadlineIsFuture() throws Exception {
        Optional<UUID> userId = Optional.of(UUID.fromString("be288274-1773-4181-a234-e8349b6b07ab"));
        when(auditorAware.getCurrentAuditor()).thenReturn(userId);

        Instant deadline = Instant.now().minus(10, ChronoUnit.DAYS);
        String title = "newTitle";
        String description = "newdescription";
        TaskUpdateRequest updateRequest = new TaskUpdateRequest()
                .setTitle(title)
                .setDescription(description)
                .setDeadline(deadline)
                .setPriority(Priority.CRITICAL);

        String taskId = "1";
        mvc.perform(put("/api/v1/task/"  + taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().is4xxClientError());

    }





}
