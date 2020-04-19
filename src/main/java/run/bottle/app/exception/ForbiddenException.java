package run.bottle.app.exception;

import org.springframework.http.HttpStatus;

/**
 * 禁止访问异常
 *
 * @author lycheng
 * @date 2020/4/18 20:57
 */
public class ForbiddenException extends BottleException {

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
