package run.bottle.app;

import cn.hutool.crypto.digest.BCrypt;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import run.bottle.app.model.entity.User;
import run.bottle.app.service.UserService;

@SpringBootTest
public class UserTest {

    @Autowired
    private UserService userService;

    @Test
    public void initUser(){
        userService.removeAll();
        User user = new User();
        user.setNickname("admin");
        user.setUsername("admin");
        user.setEmail("38707145@qq.com");
        user.setPassword(BCrypt.hashpw("123456"));
        user.setAvatar("/avatar2.jpg");
        user.prePersist();
        userService.create(user);
    }
}
