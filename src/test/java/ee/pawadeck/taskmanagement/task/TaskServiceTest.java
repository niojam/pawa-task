package ee.pawadeck.taskmanagement.task;

import ee.pawadeck.taskmanagement.comment.CommentService;
import ee.pawadeck.taskmanagement.task.dto.TaskCreateRequest;
import ee.pawadeck.taskmanagement.task.dto.TaskDto;
import ee.pawadeck.taskmanagement.task.dto.TaskUpdateRequest;
import ee.pawadeck.taskmanagement.task.mapper.TaskMapper;
import ee.pawadeck.taskmanagement.task.model.Priority;
import ee.pawadeck.taskmanagement.task.model.TaskModel;
import ee.pawadeck.taskmanagement.task.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private CommentService commentService;

    @InjectMocks
    private TaskService taskService;


    @Test
    public void shouldCreateTask() {
        Instant deadline = Instant.parse("2023-12-05T18:00:00Z");
        String title = "title";
        TaskCreateRequest createRequest = new TaskCreateRequest()
                .setTitle(title)
                .setComment("commnet")
                .setDeadline(deadline)
                .setPriority(Priority.LOW);

        TaskModel taskModel = new TaskModel()
                .setTitle(title)
                .setDeadline(deadline)
                .setPriority(Priority.LOW);

        TaskDto taskDto = new TaskDto()
                .setTitle(title)
                .setPriority(Priority.LOW)
                .setDeadline(deadline);

        when(taskRepository.save(any(TaskModel.class))).thenReturn(taskModel);
        when(taskMapper.toDto(taskModel)).thenReturn(taskDto);

        TaskDto createdTask = taskService.create(createRequest);

        verify(commentService, times(1)).create(any());
        assertThat(createdTask.getTitle()).isEqualTo(title);
        assertThat(createdTask.getPriority()).isEqualTo(Priority.LOW);
        assertThat(createdTask.getDeadline()).isEqualTo(deadline);
    }


    @Test
    public void shouldUpdateTask() {
        Long taskId = 1L;
        Instant deadline = Instant.parse("2023-12-05T18:00:00Z");
        String title = "new";
        String description = "description";

        TaskUpdateRequest updateRequest = new TaskUpdateRequest().setTitle(title)
                .setDescription(description);
        TaskModel taskModel = new TaskModel()
                .setTitle(title)
                .setDeadline(deadline)
                .setPriority(Priority.HIGH);

        TaskDto taskDto = new TaskDto()
                .setTitle(title)
                .setPriority(Priority.HIGH)
                .setDeadline(deadline);

        when(taskRepository.requireById(any(Long.class))).thenReturn(taskModel);

        taskService.update(taskId, updateRequest);
        verify(taskMapper, times(1)).update(taskModel, updateRequest);
        verify(taskRepository, times(1)).save(any(TaskModel.class));
    }


    @Test
    public void shouldDelete() {
        Long taskId = 1L;

        taskService.delete(taskId);

        verify(commentService, times(1)).deleteAllByTaskId(taskId);
        verify(taskRepository, times(1)).deleteById(taskId);
    }


}