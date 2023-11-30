package ee.pawadeck.taskmanagement.user;

import ee.pawadeck.taskmanagement.user.client.UserApiClient;
import ee.pawadeck.taskmanagement.user.dto.UserInfoDto;
import ee.pawadeck.taskmanagement.user.mapper.UserInfoMapper;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserInfoProvider {

    private final UserApiClient userApiClient;
    private final UserInfoMapper userInfoMapper;

    public UserInfoDto getUserInfoById(UUID id) {
        UserRepresentation userRepresentation = userApiClient.getUserInfoById(id);
        return userInfoMapper.toDto(userRepresentation);
    }

}
