package run.bottle.app.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import run.bottle.app.filter.CorsFilter;

/**
 * Bottle configuration.
 *
 * @author lycheng
 * @date 2020/4/18 20:57
 */
@Configuration
@Slf4j
public class BottleConfiguration {

    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        builder.failOnEmptyBeans(false);
        return builder.build();
    }

    /**
     * Creates a CorsFilter.
     *
     * @return Cors filter registration bean
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> corsFilter = new FilterRegistrationBean<>();

        corsFilter.setOrder(Ordered.HIGHEST_PRECEDENCE + 10);
        corsFilter.setFilter(new CorsFilter());
        corsFilter.addUrlPatterns("/*");

        return corsFilter;
    }

}
