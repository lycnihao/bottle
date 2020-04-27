package run.bottle.app.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author lycheng
 * @date 2020/4/26 21:26
 */
@Data
@Entity
@Table(name = "file_info")
@ToString(callSuper = true)
public class FileInfo implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String identifier;

    @Column(nullable = false)
    private Long totalSize;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String location;
}
