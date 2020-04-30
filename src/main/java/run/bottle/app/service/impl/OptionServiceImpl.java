package run.bottle.app.service.impl;

import java.util.function.Supplier;
import org.springframework.stereotype.Service;
import run.bottle.app.exception.NotFoundException;
import run.bottle.app.model.entity.Option;
import run.bottle.app.repository.OptionRepository;
import run.bottle.app.service.OptionService;
import run.bottle.app.service.base.AbstractCrudService;

/**
 * 描述
 *
 * @author LiYuanCheng
 * @date 2020-04-30 11:29
 */
@Service
public class OptionServiceImpl extends AbstractCrudService<Option, Integer> implements OptionService {

  private final OptionRepository optionRepository;

  protected OptionServiceImpl(OptionRepository optionRepository) {
    super(optionRepository);
    this.optionRepository = optionRepository;
  }


  @Override
  public Option findByKey(String key) {
    return optionRepository.findByKey(key).orElse(null);
  }

  @Override
  public Option findByKeyOfNonNull(String key) {
    return optionRepository.findByKey(key).orElseThrow(() -> new NotFoundException("The key does not exist").setErrorData(key));
  }
}
