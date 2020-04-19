package run.bottle.app.exception;

import org.springframework.http.HttpStatus;

/**
 * 服务引起的异常
 *
 * @author lycheng
 * @date 2020/4/18 20:57
 */
public class ServiceException extends BottleException {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
