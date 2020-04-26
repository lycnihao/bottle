package run.bottle.app.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import run.bottle.app.model.entity.Chunk;
import run.bottle.app.repository.base.BaseRepository;

public interface ChunkRepository extends BaseRepository<Chunk, Integer> , JpaSpecificationExecutor<Chunk> {
}
