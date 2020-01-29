package artezio.vkolodynsky.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityWebApplicationInitializer
        extends AbstractSecurityWebApplicationInitializer {

    public SecurityWebApplicationInitializer() {
        super(WebConfig.class, SecurityConfig.class, LiquibaseConfig.class, PersistenceConfig.class);
    }
}