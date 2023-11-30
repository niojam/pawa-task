package ee.pawadeck.taskmanagement.task.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.util.UUID;

@Data
public class AuditableEntity {

    @CreatedDate
    private Instant createdAt;

    @CreatedBy
    private UUID createdBy;

    @LastModifiedDate
    private Instant modifiedAt;

    @LastModifiedBy
    private UUID modifiedBy;


}
