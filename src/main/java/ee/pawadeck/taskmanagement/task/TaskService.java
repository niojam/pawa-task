package ee.pawadeck.taskmanagement.task;

import ee.pawadeck.taskmanagement.comment.CommentService;
import ee.pawadeck.taskmanagement.comment.dto.CommentCreateRequest;
import ee.pawadeck.taskmanagement.comment.dto.CommentDto;
import ee.pawadeck.taskmanagement.task.dto.TaskCreateRequest;
import ee.pawadeck.taskmanagement.task.dto.TaskDto;
import ee.pawadeck.taskmanagement.task.dto.TaskExtendedData;
import ee.pawadeck.taskmanagement.task.dto.TaskUpdateRequest;
import ee.pawadeck.taskmanagement.task.mapper.TaskMapper;
import ee.pawadeck.taskmanagement.task.model.TaskModel;
import ee.pawadeck.taskmanagement.task.repository.TaskRepository;
import ee.pawadeck.taskmanagement.user.UserInfoProvider;
import ee.pawadeck.taskmanagement.user.dto.UserInfoDto;
import ee.pawadeck.tasks.EventType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository repository;
    private final TaskMapper mapper;
    private final CommentService commentService;
    private final UserInfoProvider userInfoProvider;
    private final TaskEventProducer taskEventProducer;


    public TaskDto create(TaskCreateRequest request) {
        TaskModel taskModel = new TaskModel();
        mapper.fillModel(taskModel, request);
        TaskModel saved = repository.save(taskModel);

        if (request.getComment() != null && !request.getComment().isBlank()) {
            commentService.create(new CommentCreateRequest()
                    .setTaskId(saved.getId())
                    .setContent(request.getComment())
            );
        }
        TaskDto taskDto = mapper.toDto(saved);
        taskEventProducer.sendToKafka(taskDto, EventType.CREATED);
        return taskDto;
    }

    public void update(Long id, TaskUpdateRequest request) {
        TaskModel taskModel = repository.requireById(id);
        mapper.update(taskModel, request);

        TaskDto taskDto = mapper.toDto(repository.save(taskModel));
        taskEventProducer.sendToKafka(taskDto, EventType.UPDATED);

    }

    public void delete(Long id) {
        Optional<TaskModel> taskModel = repository.findById(id);
        commentService.deleteAllByTaskId(id);
        repository.deleteById(id);
        taskModel.ifPresent(model -> taskEventProducer.sendToKafka(mapper.toDto(model), EventType.DELETED));
    }

    @Transactional(readOnly = true)
    public TaskExtendedData getTaskFullData(Long id) {
        TaskModel taskModel = repository.requireById(id);

        UserInfoDto createdBy = userInfoProvider.getUserInfoById(taskModel.getCreatedBy());
        UserInfoDto lastEditBy = userInfoProvider.getUserInfoById(taskModel.getModifiedBy());

        List<CommentDto> commentDtos = commentService.findAllByTaskId(id);

        return mapper.toTaskExtended(taskModel)
                .setComments(commentDtos)
                .setCreatedBy(createdBy)
                .setLastEditBy(lastEditBy);
    }

    @Transactional(readOnly = true)
    public List<TaskDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }


}
