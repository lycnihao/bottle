package run.bottle.app.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import run.bottle.app.exception.ForbiddenException;
import run.bottle.app.model.entity.User;
import run.bottle.app.repository.UserRepository;
import run.bottle.app.service.UserService;
import run.bottle.app.service.base.AbstractCrudService;
import run.bottle.app.utils.BottleUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl extends AbstractCrudService<User, Integer> implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User getByUsernameOfNonNull(String username) {
        return getByUsername(username);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getByEmailOfNonNull(String email) {
        return getByEmail(email);
    }

    @Override
    public void mustNotExpire(User user) {
        Date now = new Date();
        if (user.getExpireTime() != null && user.getExpireTime().after(now)){
            long seconds = TimeUnit.MILLISECONDS.toSeconds(user.getExpireTime().getTime() - now.getTime());
            throw new ForbiddenException("账号已被停用，请 " + BottleUtils.timeFormat(seconds) + " 后重试").setErrorData(seconds);
        }
    }

    @Override
    public boolean passwordMatch(User user, String plainPassword) {
        Assert.notNull(user, "User must not be null");
        return !StringUtils.isBlank(plainPassword) && BCrypt.checkpw(plainPassword, user.getPassword());
    }
}
