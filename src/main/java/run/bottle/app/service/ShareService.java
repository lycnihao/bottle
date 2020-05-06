package run.bottle.app.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import run.bottle.app.model.entity.Share;
import run.bottle.app.model.params.ShareParam;
import run.bottle.app.service.base.CrudService;

public interface ShareService extends CrudService<Share, Integer> {
  Page<Share> pageBy(ShareParam shareParam, Pageable pageable);
}
