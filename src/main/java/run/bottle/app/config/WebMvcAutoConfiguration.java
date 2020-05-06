package run.bottle.app.config;

import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;
import run.bottle.app.filter.LogFilter;
import run.bottle.app.model.support.FileConst;
import run.bottle.app.security.filter.AdminAuthenticationInterceptor;
import run.bottle.app.utils.FileUtils;

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


    public WebMvcAutoConfiguration(AdminAuthenticationInterceptor adminAuthenticationInterceptor) {
        this.adminAuthenticationInterceptor = adminAuthenticationInterceptor;
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        urlPathHelper.setUrlDecode(false);
        configurer.setUrlPathHelper(urlPathHelper);
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminAuthenticationInterceptor)
                .addPathPatterns("/**")
                .addPathPatterns("/api/**")
                .excludePathPatterns("/upload/**")
                .excludePathPatterns("/admin/**")
                .excludePathPatterns("/js/**","/css/**","/assets/**","/logo.png","/favicon.ico")
                .excludePathPatterns("/api/admin/login","/api/admin/is_installed","/api/admin/installations")
                .excludePathPatterns("/api/admin/fileManager/chunk")
                .excludePathPatterns("/install")
                .excludePathPatterns("/error");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String workDir = Paths.get(FileUtils.getWorkDir()).toAbsolutePath().toString() + DELIMITER;

        registry.addResourceHandler("upload/**")
                .addResourceLocations(FILE_PROTOCOL + workDir);

        registry.addResourceHandler("/**","admin/**")
                .addResourceLocations("classpath:/templates/admin/");

 /*       registry.addResourceHandler("/assets")
                .addResourceLocations("classpath:/templates/admin/assets");

        registry.addResourceHandler("/logo.png")
                .addResourceLocations("classpath:/templates/admin/logo.png");*/
    }
}
