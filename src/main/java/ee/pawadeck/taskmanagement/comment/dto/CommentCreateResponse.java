package ee.pawadeck.taskmanagement.comment.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;

@Data
@Accessors(chain = true)
public class CommentCreateResponse {

    @NotNull
    private Long id;

    @NotBlank
    private String content;

    @NotNull
    @Future
    private Instant createdAt;

}
