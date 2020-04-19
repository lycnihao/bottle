package run.bottle.app.model.dto;

import lombok.Data;

import java.util.List;

/**
 * 文件夹 tree node 结构
 *
 * @author lycheng
 * @date 2020/4/18 21:26
 */
@Data
public class FolderNode {
    private String key;
    private String name;
    private Boolean disabled;
    private List<FolderNode> child;
}
