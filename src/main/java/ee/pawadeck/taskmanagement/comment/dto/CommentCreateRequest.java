package ee.pawadeck.taskmanagement.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CommentCreateRequest {

    @NotNull
    private Long taskId;

    @NotBlank
    private String content;

}
