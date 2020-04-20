package run.bottle.app.security.filter;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import run.bottle.app.cache.LocalCache;
import run.bottle.app.exception.AuthenticationException;
import run.bottle.app.exception.BottleException;
import run.bottle.app.model.entity.User;
import run.bottle.app.model.support.UserDetail;
import run.bottle.app.security.context.SecurityContextHolder;
import run.bottle.app.security.handler.AuthenticationFailureHandler;
import run.bottle.app.security.handler.DefaultAuthenticationFailureHandler;
import run.bottle.app.security.util.SecurityUtils;
import run.bottle.app.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static run.bottle.app.model.support.BottleConst.API_ACCESS_KEY_HEADER_NAME;

/**
 * 身份验证过滤器
 *
 * @author lycheng
 * @date 2020/4/18 20:57
 */
@Slf4j
@Component
public class AdminAuthenticationInterceptor implements HandlerInterceptor {

    private final LocalCache localCache;

    private final AuthenticationFailureHandler failureHandler = new DefaultAuthenticationFailureHandler();

    public AdminAuthenticationInterceptor(LocalCache localCache, UserService userService) {
        this.localCache = localCache;
        this.userService = userService;
    }

    private final UserService userService;



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = getTokenFromRequest(request);
        log.debug("request token:" + token);
        if (StringUtils.isBlank(token)) {
            failureHandler.onFailure(request, response, new AuthenticationException("未登录，请登陆后访问"));
            return false;
        }

        // 从缓存中获取用户ID
        Object object = localCache.get(SecurityUtils.buildTokenAccessKey(token));
        if (ObjectUtils.isEmpty(object)){
            failureHandler.onFailure(request, response, new AuthenticationException("Token 已过期或不存在").setErrorData(token));
            return false;
        }

        // 获取用户
        User user = userService.getById(Integer.valueOf(object.toString()));
        log.debug(user.toString());
        //保存用户信息
        SecurityContextHolder.setContext(new UserDetail(user));

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }

    protected String getTokenFromRequest(HttpServletRequest request) {
        // Get from header
        String accessKey = request.getHeader(API_ACCESS_KEY_HEADER_NAME);

        // Get from param
        if (StringUtils.isBlank(accessKey)) {
            accessKey = request.getParameter(API_ACCESS_KEY_HEADER_NAME);
        }
        return accessKey;
    }
}
