package run.bottle.app.exception;

/**
 * 未安装引起的异常
 *
 * @author lycheng
 * @date 2020/4/18 20:57
 */
public class NotInstallException extends ServiceException {

    public NotInstallException(String message) {
        super(message);
    }

    public NotInstallException(String message, Throwable cause) {
        super(message, cause);
    }
}
