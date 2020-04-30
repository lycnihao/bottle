package run.bottle.app.repository;

import java.util.Optional;
import run.bottle.app.model.entity.Option;
import run.bottle.app.repository.base.BaseRepository;


public interface OptionRepository extends BaseRepository<Option, Integer> {

    Optional<Option> findByKey(String key);

    void deleteByKey(String key);
}
