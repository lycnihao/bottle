package run.bottle.app.service;


import run.bottle.app.model.entity.Option;
import run.bottle.app.service.base.CrudService;

public interface OptionService extends CrudService<Option, Integer> {

  Option findByKey(String key);

  Option findByKeyOfNonNull(String key);
}
