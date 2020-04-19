package run.bottle.app;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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


    /**
     * Index redirect uri.
     */
    private final static String INDEX_REDIRECT_URI = "index.html";

    /**
     * Install redirect uri.
     */
    private final static String INSTALL_REDIRECT_URI = INDEX_REDIRECT_URI + "#install";

    @GetMapping("admin")
    public void admin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String adminIndexRedirectUri = StringUtils.appendIfMissing("admin", "/") + INDEX_REDIRECT_URI;
        System.out.println("INDEX_REDIRECT_URI-"+INDEX_REDIRECT_URI);
        response.sendRedirect(adminIndexRedirectUri);
    }

    @GetMapping("install")
    public void installation(HttpServletResponse response) throws IOException {
        String installRedirectUri = StringUtils.appendIfMissing("admin", "/") + INSTALL_REDIRECT_URI;
        System.out.println(installRedirectUri);
        response.sendRedirect(installRedirectUri);
    }

}
