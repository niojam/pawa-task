package ee.pawadeck.taskmanagement.comment.model;

import ee.pawadeck.taskmanagement.task.model.AuditableEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table("comment")
public class CommentModel extends AuditableEntity {

    @Id
    private Long id;

    private Long taskId;

    private String content;

}
