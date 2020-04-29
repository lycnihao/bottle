package run.bottle.app;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@SpringBootApplication
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> connector.setProperty("relaxedQueryChars", "|{}[]\\"));
        return factory;
    }

    /**
     * 后台重定向路径
     */
    private final static String INDEX_REDIRECT_URI = "index.html";

    /**
     * 安装路径
     */
    private final static String INSTALL_REDIRECT_URI = INDEX_REDIRECT_URI + "#install";

    @GetMapping("${bottle.admin-path:admin}")
    public void admin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String adminIndexRedirectUri = StringUtils.appendIfMissing("admin", "/") + INDEX_REDIRECT_URI;
        System.out.println("INDEX_REDIRECT_URI-"+INDEX_REDIRECT_URI);
        response.sendRedirect(adminIndexRedirectUri);
    }

    @GetMapping("install")
    public void installation(HttpServletResponse response) throws IOException {
        String installRedirectUri = StringUtils.appendIfMissing("admin", "/") + INSTALL_REDIRECT_URI;
        response.sendRedirect(installRedirectUri);
    }

}
