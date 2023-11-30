package ee.pawadeck.taskmanagement.comment.dto;

import ee.pawadeck.taskmanagement.user.dto.UserInfoDto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;

@Data
@Accessors(chain = true)
public class CommentDto {

    private Long id;
    private String content;
    private Instant createdAt;
    private UserInfoDto createdBy;

}
