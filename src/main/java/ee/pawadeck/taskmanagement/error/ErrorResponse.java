package ee.pawadeck.taskmanagement.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.validation.FieldError;

import java.time.Instant;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(NON_EMPTY)
public class ErrorResponse {

    private Instant timestamp = Instant.now();

    private GeneralError error;

    private List<FieldError> fieldErrors;

    public ErrorResponse(GeneralError error, List<FieldError> fieldErrors) {
        this.error = error;
        this.fieldErrors = fieldErrors;
    }

    public ErrorResponse(GeneralError error) {
        this.error = error;
    }

    public static ErrorResponse errorResponse(String errorCode, String message) {
        return new ErrorResponse(new GeneralError(errorCode, message));
    }

}