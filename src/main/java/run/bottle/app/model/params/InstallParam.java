package run.bottle.app.model.params;

import lombok.Data;
import lombok.ToString;
import run.bottle.app.model.entity.User;

/**
 * 描述
 *
 * @author LiYuanCheng
 * @date 2020-05-06 10:03
 */
@Data
@ToString
public class InstallParam extends User {

  private String title;

  private String url;

}
