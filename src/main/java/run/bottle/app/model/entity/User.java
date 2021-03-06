package run.bottle.app.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户
 *
 * @author lycheng
 * @date 2020/4/18 21:26
 */
@Data
@Entity
@Table(name = "users")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 用户名称
     */
    @Column(name = "username", columnDefinition = "varchar(50) not null")
    private String username;

    /**
     * 用户昵称
     */
    @Column(name = "nickname", columnDefinition = "varchar(255) not null")
    private String nickname;

    /**
     * 密码
     */
    @Column(name = "password", columnDefinition = "varchar(255) not null")
    private String password;

    /**
     * 邮箱
     */
    @Column(name = "email", columnDefinition = "varchar(127) default ''")
    private String email;

    /**
     * 用户头像
     */
    @Column(name = "avatar", columnDefinition = "varchar(1023) default ''")
    private String avatar;

    /**
     * 到期时间
     */
    @Column(name = "expire_time", columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expireTime;


    @Override
    public void prePersist() {
        super.prePersist();

        id = null;

        if (email == null) {
            email = "";
        }

        if (avatar == null) {
            avatar = "";
        }

        if (expireTime == null) {
            expireTime = new Date();
        }
    }
}
