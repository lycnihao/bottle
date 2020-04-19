package run.bottle.app.repository;

import run.bottle.app.model.entity.User;
import run.bottle.app.repository.base.BaseRepository;



public interface UserRepository extends BaseRepository<User, Integer> {

    User findByUsername(String username);

    User findByEmail(String email);
}
