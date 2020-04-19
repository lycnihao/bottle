package run.bottle.app.model.support;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import run.bottle.app.model.entity.User;

/**
 * 用户详细资料
 *
 * @author lycheng
 * @date 2020/4/18 20:57
 */
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserDetail {

    private User user;


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
        this.user.setPassword("***");
    }
}
