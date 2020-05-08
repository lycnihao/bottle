package run.bottle.app.controller;

import static org.springframework.data.domain.Sort.Direction.DESC;
import static run.bottle.app.utils.FileUtils.generatePath;
import static run.bottle.app.utils.FileUtils.getWorkDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.bottle.app.model.entity.Share;
import run.bottle.app.model.enums.ExpiredTypeEnums;
import run.bottle.app.model.params.SharesParam;
import run.bottle.app.model.support.BaseResponse;
import run.bottle.app.service.ShareService;
import run.bottle.app.utils.FileHashCode;

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
  public List<Share> listAll() {
    return shareService.listAll();
  }

  @PutMapping
  public BaseResponse add(@RequestBody SharesParam sharesParam) {
    Path pathObj = Paths.get(getWorkDir() ,sharesParam.getPath());
    Share share = new Share();
    try {
      BasicFileAttributes attrs = Files.readAttributes(pathObj, BasicFileAttributes.class);
      String mediaType = Files.probeContentType(pathObj);
      share.setFileKey(FileHashCode.md5HashCode(pathObj.toAbsolutePath().toString()));
      share.setTitle(pathObj.getFileName().toString() );
      share.setSize(attrs.size());
      share.setPath(sharesParam.getPath());
      share.setMediaType( StringUtils.isEmpty(mediaType) ? "" : MediaType.valueOf(mediaType).toString());
      share.setCreateTime(new Date());
      long timeMillis = 24 * 60 * 60 * 1000;
      if (sharesParam.getExpiredType() == ExpiredTypeEnums.DAY.getCode()) {
        share.setExpiredTime(new Date(System.currentTimeMillis() + timeMillis));
      } else if (sharesParam.getExpiredType() == ExpiredTypeEnums.WEEK.getCode()) {
        share.setExpiredTime(new Date(System.currentTimeMillis() + (7 * timeMillis) ));
      } else if (sharesParam.getExpiredType() == ExpiredTypeEnums.MONTH.getCode()) {
        share.setExpiredTime(new Date(System.currentTimeMillis() + (30 * timeMillis) ));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    shareService.create(share);
    return BaseResponse.ok("分享成功");
  }

  @DeleteMapping
  public BaseResponse remove(String key) {
    Share share = shareService.findByKey(key);
    shareService.remove(share);
    return BaseResponse.ok("删除成功");
  }
}
