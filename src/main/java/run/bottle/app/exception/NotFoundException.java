package run.bottle.app.exception;

import org.springframework.http.HttpStatus;

/**
 * 找不到实体异常
 *
 * @author lycheng
 * @date 2020/4/18 20:57
 */
public class NotFoundException extends BottleException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
