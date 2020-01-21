package artezio.vkolodynsky.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan({"artezio.vkolodynsky.controller.administration", "artezio.vkolodynsky.controller"})
@PropertySource({"classpath:cors.properties"})
@Slf4j
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private Environment env;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/administration/**")
                .allowedOrigins(env.getProperty("administration.origin"))
                .allowedMethods("*");
    }
}
