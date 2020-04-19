package run.bottle.app.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import run.bottle.app.filter.LogFilter;
import run.bottle.app.security.filter.AdminAuthenticationInterceptor;

import static run.bottle.app.model.support.FileConst.DELIMITER;
import static run.bottle.app.model.support.FileConst.USER_HOME;

/**
 * WebMvc configuration.
 *
 * @author lycheng
 * @date 2020/4/18 20:57
 */
@Slf4j
@Configuration
@EnableWebMvc
public class WebMvcAutoConfiguration implements WebMvcConfigurer {

    private static final String FILE_PROTOCOL = "file:///";

    private final AdminAuthenticationInterceptor adminAuthenticationInterceptor;

    private final LogFilter logFilter;

    public WebMvcAutoConfiguration(AdminAuthenticationInterceptor adminAuthenticationInterceptor, LogFilter logFilter) {
        this.adminAuthenticationInterceptor = adminAuthenticationInterceptor;
        this.logFilter = logFilter;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logFilter)
                .addPathPatterns("/**")
                .excludePathPatterns("/admin/index.html")
                .excludePathPatterns("/install");

        registry.addInterceptor(adminAuthenticationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/upload/**")
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login")
                .excludePathPatterns("/admin/index.html")
                .excludePathPatterns("/install")
                .excludePathPatterns("/error")
                .excludePathPatterns( "/js/**","/css/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String workDir = USER_HOME + DELIMITER + ".bottle" + DELIMITER;

        registry.addResourceHandler("upload/**")
                .addResourceLocations(FILE_PROTOCOL + workDir + "upload/");

        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/templates/admin/");

        registry.addResourceHandler("admin/**")
                .addResourceLocations("classpath:/templates/admin/");
    }
}