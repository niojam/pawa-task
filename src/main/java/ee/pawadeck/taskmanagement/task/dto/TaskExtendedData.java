package ee.pawadeck.taskmanagement.task.dto;

import ee.pawadeck.taskmanagement.comment.dto.CommentDto;
import ee.pawadeck.taskmanagement.task.model.Priority;
import ee.pawadeck.taskmanagement.user.dto.UserInfoDto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.List;

@Data
@Accessors(chain = true)
public class TaskExtendedData {

    private Long id;

    private String title;

    private String description;

    private Priority priority;

    private Instant deadline;

    private UserInfoDto createdBy;

    private UserInfoDto lastEditBy;

    private List<CommentDto> comments;

}
