package run.bottle.app.model.support;

import org.springframework.http.HttpHeaders;

import java.time.Duration;

/**
 * 公共常量
 *
 * @author lycheng
 * @date 2020/4/18 20:57
 */
public class BottleConst {


    /**
     * token标识名称
     */
    public final static String API_ACCESS_KEY_HEADER_NAME = "API-" + HttpHeaders.AUTHORIZATION;


    public final static Duration TEMP_TOKEN_EXPIRATION = Duration.ofDays(7);

    /**
     * 用户会话
     */
    public static String ADMIN_SESSION_KEY = "admin_session";

    /**
     * 首页路径
     */
    public static final String BOTTLE_PATH = "http://localhost:8090";

    /**
     * 路径分隔符
     */
    public static final String DELIMITER = "/";
}
