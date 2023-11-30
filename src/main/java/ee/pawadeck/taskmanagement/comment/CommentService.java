package ee.pawadeck.taskmanagement.comment;

import ee.pawadeck.taskmanagement.comment.dto.CommentCreateRequest;
import ee.pawadeck.taskmanagement.comment.dto.CommentCreateResponse;
import ee.pawadeck.taskmanagement.comment.dto.CommentDto;
import ee.pawadeck.taskmanagement.comment.mapper.CommentMapper;
import ee.pawadeck.taskmanagement.comment.model.CommentModel;
import ee.pawadeck.taskmanagement.comment.repository.CommentRepository;
import ee.pawadeck.taskmanagement.user.UserInfoProvider;
import ee.pawadeck.taskmanagement.user.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository repository;
    private final CommentMapper mapper;
    private final UserInfoProvider userInfoProvider;

    public CommentCreateResponse create(CommentCreateRequest createRequest) {
        CommentModel model = new CommentModel();
        mapper.fillModel(model, createRequest);
        CommentModel saved = repository.save(model);
        return mapper.toCreateResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<CommentDto> findAllByTaskId(Long taskId) {
        return repository.findAllByTaskId(taskId)
                .stream()
                .map(this::getCommentDtoUserInfoEnriched)
                .toList();
    }

    private CommentDto getCommentDtoUserInfoEnriched(CommentModel comment) {
        UserInfoDto createdBy = userInfoProvider.getUserInfoById(comment.getCreatedBy());
        return mapper.toDto(comment).setCreatedBy(createdBy);
    }

    public void deleteAllByTaskId(Long taskId) {
        repository.deleteAllByTaskId(taskId);
    }

    @Transactional(readOnly = true)
    public CommentDto require(Long id) {
        CommentModel model = repository.requireById(id);
        return getCommentDtoUserInfoEnriched(model);
    }



}
