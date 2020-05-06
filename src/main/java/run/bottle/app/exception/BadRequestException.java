package run.bottle.app.exception;

import org.springframework.http.HttpStatus;

/**
 * 错误请求导致的异常
 *
 * @author LiYuanCheng
 * @date 2020-05-06 10:09
 */
public class BadRequestException  extends BottleException {

  public BadRequestException(String message) {
    super(message);
  }

  public BadRequestException(String message, Throwable cause) {
    super(message, cause);
  }

  @Override
  public HttpStatus getStatus() {
    return HttpStatus.FORBIDDEN;
  }
}
