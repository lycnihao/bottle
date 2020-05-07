package run.bottle.app.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import lombok.ToString;

/**
 * 分享
 *
 * @author LiYuanCheng
 * @date 2020-05-06 15:34
 */
@Data
@Entity
@Table(name = "share")
@ToString
public class Share {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "custom-id")
  private Integer id;

  /**
   * 附件名称
   */
  @Column(name = "title", nullable = false)
  private String title;

  /**
   * 文件KEY
   */
  @Column(name = "file_key", length = 2047)
  private String fileKey;

  /**
   * 访问路径
   */
  @Column(name = "path", length = 1023, nullable = false)
  private String path;

  /**
   * 附件类型
   */
  @Column(name = "media_type", length = 127, nullable = false)
  private String mediaType;

  /**
   * 附件大小
   */
  @Column(name = "size", nullable = false)
  private Long size;

  /**
   * 加密
   */
  @Column(name = "password")
  private String password;

  /**
   * 分享时间
   */
  @Column(name = "create_time", nullable = false)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createTime;

  /**
   * 失效时间
   */
  @Column(name = "expired_time")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Temporal(TemporalType.TIMESTAMP)
  private Date expiredTime;

}
