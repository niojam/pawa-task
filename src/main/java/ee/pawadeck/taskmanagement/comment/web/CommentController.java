package ee.pawadeck.taskmanagement.comment.web;

import ee.pawadeck.taskmanagement.comment.CommentService;
import ee.pawadeck.taskmanagement.comment.dto.CommentCreateRequest;
import ee.pawadeck.taskmanagement.comment.dto.CommentCreateResponse;
import ee.pawadeck.taskmanagement.comment.dto.CommentDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary =
            "Create new comment"
    )
    @PostMapping("comment")
    @ResponseStatus( HttpStatus.CREATED )
    public CommentCreateResponse create(@RequestBody @Valid CommentCreateRequest request) {
        return commentService.create(request);
    }

    @Operation(summary =
            "Get comment by id"
    )
    @GetMapping("{id}")
    public CommentDto require(@PathVariable Long id) {
        return commentService.require(id);
    }

}
