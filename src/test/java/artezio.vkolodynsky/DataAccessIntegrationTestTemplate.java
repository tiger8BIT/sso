package artezio.vkolodynsky;
import artezio.vkolodynsky.config.PersistenceConfig;
import artezio.vkolodynsky.config.WebConfig;
import artezio.vkolodynsky.service.AppService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class, WebConfig.class}, loader = AnnotationConfigContextLoader.class)
public class DataAccessIntegrationTestTemplate {
    @Autowired
    private AppService appService;

    @Test
    public void testDataAccess() {
        appService.findAll();
    }
}