package run.bottle.app.model.dto;

import lombok.Data;

/**
 * 响应文件DTO
 *
 * @author lycheng
 * @date 2020/4/18 21:26
 */
@Data
public class FileItemDTO {
    private String name;
    private String date;
    private long size;
    private String mediaType;
    private Boolean isDirectory;
}
