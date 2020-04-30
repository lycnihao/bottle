package run.bottle.app.model.params;

import lombok.Data;
import lombok.ToString;
import run.bottle.app.model.entity.FileInfo;

@Data
@ToString
public class MergeFileParam extends FileInfo {

  /**
   * 上传路径
   */
  private String path;

}
