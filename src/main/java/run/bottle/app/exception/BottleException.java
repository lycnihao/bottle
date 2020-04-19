package run.bottle.app.exception;

import org.springframework.http.HttpStatus;

/**
 * Base exception
 *
 * @author lycheng
 * @date 2020/4/18 20:57
 */
public abstract class BottleException extends RuntimeException {
    /**
     * Error errorData.
     */
    private Object errorData;

    public BottleException(String message) {
        super(message);
    }

    public BottleException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract HttpStatus getStatus();

    public Object getErrorData() {
        return errorData;
    }

    public BottleException setErrorData(Object errorData) {
        this.errorData = errorData;
        return this;
    }

}
