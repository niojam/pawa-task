package ee.pawadeck.taskmanagement;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

class TestcontainersInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    static MySQLContainer<?> mysql = new MySQLContainer<>(DockerImageName.parse("mysql:8.2.0"))
            .withDatabaseName("task")
            .withUsername("test")
            .withPassword("test");
    static {
        Startables.deepStart(mysql).join();
    }


    @Override
    public void initialize(ConfigurableApplicationContext ctx) {
        TestPropertyValues.of(
                "spring.datasource.url=" + mysql.getJdbcUrl(),
                "spring.datasource.username=" + mysql.getUsername(),
                "spring.datasource.password=" + mysql.getPassword(),
                "spring.liquibase.url=" + mysql.getJdbcUrl(),
                "spring.liquibase.user=" + mysql.getUsername(),
                "spring.liquibase.password=" + mysql.getPassword()
        ).applyTo(ctx.getEnvironment());
    }

}
