package ee.pawadeck.taskmanagement.task.mapper;

import ee.pawadeck.taskmanagement.comment.mapper.CommentMapper;
import ee.pawadeck.taskmanagement.task.dto.TaskCreateRequest;
import ee.pawadeck.taskmanagement.task.dto.TaskDto;
import ee.pawadeck.taskmanagement.task.dto.TaskExtendedData;
import ee.pawadeck.taskmanagement.task.dto.TaskUpdateRequest;
import ee.pawadeck.taskmanagement.task.model.TaskModel;
import ee.pawadeck.tasks.TaskEvent;
import org.mapstruct.*;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = CommentMapper.class)
public interface TaskMapper {

    @Mapping(target = "lastEditBy", source = "modifiedBy")
    TaskDto toDto(TaskModel entity);

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "lastEditBy", ignore = true)
    TaskExtendedData toTaskExtended(TaskModel entity);

    @Mapping(target = "taskId", source = "id")
    @Mapping(target = "createdBy", source = "createdBy", qualifiedByName = "uuidToString")
    @Mapping(target = "lastEditBy", source = "lastEditBy", qualifiedByName = "uuidToString")
    @Mapping(target = "eventType", ignore = true)
    TaskEvent toTaskEvent(TaskDto dto);

    @Named("uuidToString")
    default String uuidToString(UUID id) {
        return id == null ? null : id.toString();
    }


    void fillModel(@MappingTarget TaskModel task, TaskCreateRequest taskCreateRequest);

    void update(@MappingTarget TaskModel task, TaskUpdateRequest taskUpdateRequest);

}
