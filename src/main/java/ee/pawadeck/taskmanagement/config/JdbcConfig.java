package ee.pawadeck.taskmanagement.config;

import ee.pawadeck.taskmanagement.config.audit.AuditorAwareImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.lang.NonNull;

import java.nio.ByteBuffer;
import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Configuration
@RequiredArgsConstructor
@EnableJdbcAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
public class JdbcConfig extends AbstractJdbcConfiguration {

    private final Clock clock;

    @Bean
    public AuditorAware<UUID> auditorAware() {
        return new AuditorAwareImpl();
    }

    @Bean
    DateTimeProvider auditingDateTimeProvider() {
        return () -> Optional.of(Instant.now(clock));
    }


    @Bean
    @NonNull
    @Override
    public JdbcCustomConversions jdbcCustomConversions() {
        return new JdbcCustomConversions(
                List.of(
                        new UUIDWriteConverter(),
                        new UUIDReadConverter()
                )
        );
    }

    @WritingConverter
    public static class UUIDWriteConverter implements Converter<UUID, byte[]> {
        @Override
        public byte[] convert(final UUID source) {
            final var bb = ByteBuffer.wrap(new byte[16]);
            bb.putLong(source.getMostSignificantBits());
            bb.putLong(source.getLeastSignificantBits());
            return bb.array();
        }
    }

    @ReadingConverter
    public static class UUIDReadConverter implements Converter<byte[], UUID> {
        @Override
        public UUID convert(final byte[] source) {
            final var bb = ByteBuffer.wrap(source);
            final var firstLong = bb.getLong();
            final var secondLong = bb.getLong();
            return new UUID(firstLong, secondLong);
        }
    }

}

