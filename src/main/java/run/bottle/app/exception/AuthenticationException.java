package run.bottle.app.exception;

import org.springframework.http.HttpStatus;

/**
 * 认证异常
 *
 * @author lycheng
 * @date 2020/4/18 20:57
 */
public class AuthenticationException extends BottleException {

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
