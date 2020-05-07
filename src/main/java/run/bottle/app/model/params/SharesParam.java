package run.bottle.app.model.params;

import lombok.Data;
import lombok.ToString;

/**
 * 描述
 *
 * @author LiYuanCheng
 * @date 2020-05-07 13:52
 */
@Data
@ToString
public class SharesParam {

  /**
   * 路径
   */
  private String path;

  /**
   * 是否加密
   */
  private Boolean encryption;

  /**
   * 过期时间类型
   */
  private int expiredType;
}
