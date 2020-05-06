package run.bottle.app.controller;

import static org.springframework.data.domain.Sort.Direction.DESC;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.bottle.app.model.entity.Share;
import run.bottle.app.model.params.ShareParam;
import run.bottle.app.model.support.BaseResponse;
import run.bottle.app.service.ShareService;

/**
 * 文件分享
 *
 * @author LiYuanCheng
 * @date 2020-05-06 15:54
 */
@Slf4j
@RestController
@RequestMapping(value = "api/admin/shares")
public class SharesController {

  @Autowired
  private ShareService shareService;

  @GetMapping
  public Page<Share> list(@PageableDefault(sort = {"topPriority", "createTime"}, direction = DESC) Pageable pageable,
      ShareParam shareParam) {
    return shareService.pageBy(shareParam, pageable);
  }

  @PutMapping
  public BaseResponse add(String path) {
    Path pathObj = Paths.get(path);
    Share share = new Share();
    try {
      BasicFileAttributes attrs = Files.readAttributes(Paths.get(path), BasicFileAttributes.class);
      String mediaType = Files.probeContentType(pathObj);
      share.setTitle(pathObj.getFileName().toString() );
      share.setSize(attrs.size());
      share.setPath(path);
      share.setMediaType( attrs.isDirectory() || StringUtils.isEmpty(mediaType) ? null : MediaType.valueOf(mediaType).toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
    shareService.create(share);
    return BaseResponse.ok("分享成功");
  }

  @DeleteMapping
  public String remove() {
    return super.toString();
  }
}
