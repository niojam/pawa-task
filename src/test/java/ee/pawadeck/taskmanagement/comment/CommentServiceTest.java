package ee.pawadeck.taskmanagement.comment;

import ee.pawadeck.taskmanagement.comment.dto.CommentCreateRequest;
import ee.pawadeck.taskmanagement.comment.dto.CommentCreateResponse;
import ee.pawadeck.taskmanagement.comment.mapper.CommentMapper;
import ee.pawadeck.taskmanagement.comment.model.CommentModel;
import ee.pawadeck.taskmanagement.comment.repository.CommentRepository;
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
class CommentServiceTest {


    @Mock
    private CommentRepository repository;

    @Mock
    private CommentMapper mapper;


    @InjectMocks
    private CommentService commentService;


    @Test
    public void shouldCreateComment() {
        Instant createdAt = Instant.parse("2023-12-05T18:00:00Z");

        CommentCreateRequest commentCreateRequest = new CommentCreateRequest()
                .setContent("content")
                .setTaskId(1L);

        CommentCreateResponse response = new CommentCreateResponse()
                .setContent("content")
                .setCreatedAt(createdAt)
                .setId(1L);

        when(repository.save(any(CommentModel.class))).thenReturn(new CommentModel().setTaskId(1L).setId(1L));
        when(mapper.toCreateResponse(any(CommentModel.class))).thenReturn(response);

        CommentCreateResponse actual = commentService.create(commentCreateRequest);

        assertThat(actual.getContent()).isEqualTo("content");
        assertThat(actual.getCreatedAt()).isEqualTo(createdAt);
        assertThat(actual.getId()).isEqualTo(1L);
    }


}
