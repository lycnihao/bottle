package run.bottle.app.service.impl;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import run.bottle.app.model.entity.Chunk;
import run.bottle.app.repository.ChunkRepository;
import run.bottle.app.service.ChunkService;
import run.bottle.app.service.base.AbstractCrudService;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChunkServiceImpl extends AbstractCrudService<Chunk, Integer> implements ChunkService {

    private final ChunkRepository chunkRepository;

    public ChunkServiceImpl(ChunkRepository chunkRepository) {
        super(chunkRepository);
        this.chunkRepository = chunkRepository;
    }

    @Override
    public void saveChunk(Chunk chunk) {
        chunkRepository.save(chunk);
    }

    @Override
    public boolean checkChunk(String identifier, Integer chunkNumber) {
        Specification<Chunk> specification = (Specification<Chunk>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("identifier"), identifier));
            predicates.add(criteriaBuilder.equal(root.get("chunkNumber"), chunkNumber));

            return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        };

        return chunkRepository.findOne(specification).orElse(null) == null;
    }

}
