package ee.pawadeck.taskmanagement.user.mapper;

import ee.pawadeck.taskmanagement.comment.mapper.CommentMapper;
import ee.pawadeck.taskmanagement.user.dto.UserInfoDto;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = CommentMapper.class)
public interface UserInfoMapper {

    UserInfoDto toDto(UserRepresentation userRepresentation);

}
