package ee.pawadeck.taskmanagement.user.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserInfoDto {

    private UUID id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;

}
