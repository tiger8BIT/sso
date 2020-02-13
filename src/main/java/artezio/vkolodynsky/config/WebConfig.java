package artezio.vkolodynsky.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.*;

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
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/administration/**")
                .allowedOrigins(env.getProperty("administration.origin"))
                .allowedMethods("*");
        registry.addMapping("/singin")
                .allowedOrigins(env.getProperty("singin.origin"))
                .allowedMethods("*");
    }
}
