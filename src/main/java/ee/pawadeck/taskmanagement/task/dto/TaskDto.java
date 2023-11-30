package ee.pawadeck.taskmanagement.task.dto;

import ee.pawadeck.taskmanagement.task.model.Priority;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class TaskDto {

    private Long id;
    private String title;
    private String description;
    private Priority priority;
    private Instant deadline;
    private UUID createdBy;
    private UUID lastEditBy;

}
