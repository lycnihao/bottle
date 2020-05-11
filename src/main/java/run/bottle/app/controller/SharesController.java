package run.bottle.app.controller;

import static run.bottle.app.utils.FileUtils.getWorkDir;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.bottle.app.model.entity.Share;
import run.bottle.app.model.enums.ExpiredTypeEnums;
import run.bottle.app.model.params.SharesParam;
import run.bottle.app.model.support.BaseResponse;
import run.bottle.app.service.ShareService;
import run.bottle.app.utils.BottleUtils;

/**
 * 文件分享
 *
 * @author LiYuanCheng
 * @date 2020-05-06 15:54
 */
@Slf4j
@RestController
@RequestMapping(value = {"api/admin/shares"} )
public class SharesController {

  @Autowired
  private ShareService shareService;

  @GetMapping(value = "s/{key}")
  private void shareReSources(@PathVariable("key")String key, HttpServletResponse response)
      throws IOException {
    Share share = shareService.findByKey(key);
    response.sendRedirect("/s/" + encoderUrl(share.getPath()));
  }

  @GetMapping(value = "key/{key}")
  private BaseResponse<Share> share(@PathVariable("key")String key){
    Share share = shareService.findByKey(key);
    if (share == null) {
      return BaseResponse.ok("无效资源");
    }
    if (new Date().after(share.getExpiredTime())){
      return BaseResponse.ok("啊哦，来晚了，该分享文件已过期");
    }
    return BaseResponse.ok(share);
  }

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
      /*String key = FileHashCode.md5HashCode(pathObj.toAbsolutePath().toString());*/
      String key = BottleUtils.randomUUIDWithoutDash();
      share.setFileKey(key);
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

  private String encoderUrl(String str) throws UnsupportedEncodingException {
    String name = str.substring(str.lastIndexOf("/") + 1);
    String path = str.substring(0, str.lastIndexOf("/") +1);
    return path + URLEncoder.encode(name,"UTF-8");
  }
}
