package run.bottle.app.filter;

import cn.hutool.extra.servlet.ServletUtil;
import com.sun.istack.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import run.bottle.app.exception.BottleException;
import run.bottle.app.exception.NotInstallException;
import run.bottle.app.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter for logging.
 *
 * @author lycheng
 * @date 2020/4/18 20:57
 */
@Slf4j
@Component
public class LogFilter implements HandlerInterceptor {

    public LogFilter(UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String remoteAddr = ServletUtil.getClientIP(request);

        log.debug("");
        log.debug("Starting url: [{}], method: [{}], ip: [{}]", request.getRequestURL(), request.getMethod(), remoteAddr);

        // Set start time
        long startTime = System.currentTimeMillis();

        log.debug("Ending   url: [{}], method: [{}], ip: [{}], status: [{}], usage: [{}] ms", request.getRequestURL(), request.getMethod(), remoteAddr, response.getStatus(), (System.currentTimeMillis() - startTime));

        if (userService.count() == 0) {
            log.debug("当前系统还没有初始化");
            onFailure(request, response, new NotInstallException("当前系统还没有初始化"));
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }

    public void onFailure(HttpServletRequest request, HttpServletResponse response, BottleException exception) throws IOException {
        response.sendRedirect(request.getContextPath() + "/install");
    }
}
