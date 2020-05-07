package run.bottle.app.service.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import run.bottle.app.model.entity.Share;
import run.bottle.app.model.params.ShareParam;
import run.bottle.app.repository.ShareRepository;
import run.bottle.app.service.ShareService;
import run.bottle.app.service.base.AbstractCrudService;

/**
 * 描述
 *
 * @author LiYuanCheng
 * @date 2020-05-06 15:47
 */
@Service
public class ShareServiceImpl extends AbstractCrudService<Share, Integer> implements ShareService {

  private final ShareRepository shareRepository;

  public ShareServiceImpl(ShareRepository shareRepository) {
    super(shareRepository);
    this.shareRepository = shareRepository;
  }

  @Override
  public Page<Share> pageBy(ShareParam shareParam, Pageable pageable) {
    return listAll(pageable);
  }

  @Override
  public Share findByKey(String key) {
    return shareRepository.findByFileKey(key);
  }
}
