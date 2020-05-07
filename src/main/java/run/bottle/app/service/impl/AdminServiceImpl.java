package run.bottle.app.service.impl;

import cn.hutool.core.lang.Validator;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import run.bottle.app.cache.LocalCache;
import run.bottle.app.exception.BadRequestException;
import run.bottle.app.exception.NotFoundException;
import run.bottle.app.exception.ServiceException;
import run.bottle.app.model.entity.User;
import run.bottle.app.model.support.UserDetail;
import run.bottle.app.security.AuthToken;
import run.bottle.app.security.context.SecurityContextHolder;
import run.bottle.app.security.util.SecurityUtils;
import run.bottle.app.service.AdminService;
import run.bottle.app.service.UserService;
import run.bottle.app.utils.BottleUtils;


@Service
public class AdminServiceImpl implements AdminService {

    private final UserService userService;

    private final LocalCache localCache;

    public AdminServiceImpl(UserService userService, LocalCache localCache) {
        this.userService = userService;
        this.localCache = localCache;
    }


    @Override
    public AuthToken authenticate(String username, String password) {
        String mismatchTip = "用户名或者密码不正确";

        final User user;

        try {
            // Get user by username or email
            user = Validator.isEmail(username) ?
                    userService.getByEmailOfNonNull(username) : userService.getByUsernameOfNonNull(username);
        } catch (NotFoundException e) {
            throw new BadRequestException(mismatchTip);
        }

        if (ObjectUtils.isEmpty(user)) {
            throw new BadRequestException("账号或密码不匹配");
        }

        userService.mustNotExpire(user);

        if (!userService.passwordMatch(user, password)) {
            throw new BadRequestException(mismatchTip);
        }
        System.out.println("登录成功");
        return  buildAuthToken(user);
    }

    @Override
    public void clearToken() {
        UserDetail userDetail = SecurityContextHolder.getContext();

        if (userDetail == null || userDetail.getUser() == null) {
            throw new BadRequestException("您尚未登录，因此无法注销");
        }

        User user = userDetail.getUser();

        Object obj = localCache.get(SecurityUtils.buildAccessTokenKey(user));

        localCache.delete(SecurityUtils.buildTokenAccessKey((String) obj));
        localCache.delete(SecurityUtils.buildAccessTokenKey(user));
    }

    /**
     * Builds authentication token.
     *
     * @param user user info must not be null
     * @return authentication token
     */
    @NonNull
    private AuthToken buildAuthToken(@NonNull User user) {
        Assert.notNull(user, "User must not be null");

        // Generate new token
        AuthToken token = new AuthToken();

        token.setAccessToken(BottleUtils.randomUUIDWithoutDash());
        token.setExpiredIn(ACCESS_TOKEN_EXPIRED_SECONDS);
        token.setRefreshToken(BottleUtils.randomUUIDWithoutDash());

        // Cache those tokens, just for clearing
        localCache.put(SecurityUtils.buildAccessTokenKey(user), token.getAccessToken(), ACCESS_TOKEN_EXPIRED_SECONDS);
        localCache.put(SecurityUtils.buildRefreshTokenKey(user), token.getRefreshToken(), REFRESH_TOKEN_EXPIRED_DAYS);

        // Cache those tokens with user id
        localCache.put(SecurityUtils.buildTokenAccessKey(token.getAccessToken()), user.getId(), ACCESS_TOKEN_EXPIRED_SECONDS);
        localCache.put(SecurityUtils.buildTokenRefreshKey(token.getRefreshToken()), user.getId(), REFRESH_TOKEN_EXPIRED_DAYS);

        SecurityContextHolder.setContext(new UserDetail(user));
        return token;
    }
}
