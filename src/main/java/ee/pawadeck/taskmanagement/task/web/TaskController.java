package ee.pawadeck.taskmanagement.task.web;

import ee.pawadeck.taskmanagement.task.TaskService;
import ee.pawadeck.taskmanagement.task.dto.TaskCreateRequest;
import ee.pawadeck.taskmanagement.task.dto.TaskDto;
import ee.pawadeck.taskmanagement.task.dto.TaskExtendedData;
import ee.pawadeck.taskmanagement.task.dto.TaskUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @Operation(summary =
            "Create new task"
    )
    @PostMapping
    @ResponseStatus( HttpStatus.CREATED )
    public TaskDto create(@RequestBody @Valid TaskCreateRequest request) {
        return taskService.create(request);
    }


    @Operation(summary =
            "Get task detailed data."
    )
    @GetMapping("/{id}")
    public TaskExtendedData require(@PathVariable Long id) {
        return taskService.getTaskFullData(id);
    }

    @Operation(summary =
            "Update task by id"
    )
    @PutMapping("{id}")
    public void update(@PathVariable Long id, @RequestBody @Valid TaskUpdateRequest request) {
        taskService.update(id, request);
    }

    @Operation(summary =
            "Get all tasks"
    )
    @GetMapping
    public List<TaskDto> getAllTasks() {
        return taskService.findAll();
    }

    @Operation(summary =
            "Delete task by id"
    )
    @DeleteMapping("{id}")
    @ResponseStatus( HttpStatus.NO_CONTENT )
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }


}
