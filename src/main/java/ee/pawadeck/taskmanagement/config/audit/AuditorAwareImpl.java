package ee.pawadeck.taskmanagement.config.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Optional;
import java.util.UUID;

public class AuditorAwareImpl implements AuditorAware<UUID> {

    @Override
    public Optional<UUID> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return Optional.empty();
        }
        if (authentication instanceof JwtAuthenticationToken token) {
            String userId = (String) token.getTokenAttributes().get(JwtClaimNames.SUB);
            if (userId != null) {
                return Optional.of(UUID.fromString(userId));
            }
        }
        return Optional.empty();
    }

}