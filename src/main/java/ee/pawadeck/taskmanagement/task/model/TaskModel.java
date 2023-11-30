package ee.pawadeck.taskmanagement.task.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table("task")
public class TaskModel extends AuditableEntity {

    @Id
    private Long id;

    private String title;

    private String description;

    private Priority priority;

    private Instant deadline;

}