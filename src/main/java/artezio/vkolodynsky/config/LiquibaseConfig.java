package artezio.vkolodynsky.config;

import com.google.common.base.Preconditions;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@PropertySource({"classpath:liquibase.properties"})
public class LiquibaseConfig {
    @Autowired
    private Environment env;
    @Bean
    public DataSource liquibaseDataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Preconditions.checkNotNull(env.getProperty("ljdbc.driverClassName")));
        dataSource.setUrl(Preconditions.checkNotNull(env.getProperty("ljdbc.url")));
        dataSource.setUsername(Preconditions.checkNotNull(env.getProperty("ljdbc.user")));
        dataSource.setPassword(Preconditions.checkNotNull(env.getProperty("ljdbc.pass")));
        return dataSource;
    }
    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:liquibase-changeLog.xml");
        liquibase.setDataSource(liquibaseDataSource());
        return liquibase;
    }
}
