package ee.pawadeck.taskmanagement.comment.mapper;

import ee.pawadeck.taskmanagement.comment.dto.CommentCreateRequest;
import ee.pawadeck.taskmanagement.comment.dto.CommentCreateResponse;
import ee.pawadeck.taskmanagement.comment.dto.CommentDto;
import ee.pawadeck.taskmanagement.comment.model.CommentModel;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    @Mapping(target = "createdBy", ignore = true)
    CommentDto toDto(CommentModel model);

    CommentCreateResponse toCreateResponse(CommentModel model);

    List<CommentDto> toDto(List<CommentModel> model);

    CommentModel toModel(CommentCreateRequest model);

    void fillModel(@MappingTarget CommentModel model, CommentCreateRequest request);

}
