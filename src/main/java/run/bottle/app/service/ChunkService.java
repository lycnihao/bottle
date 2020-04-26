package run.bottle.app.service;


import run.bottle.app.model.entity.Chunk;
import run.bottle.app.service.base.CrudService;

public interface ChunkService  extends CrudService<Chunk, Integer>  {
    /**
     * 保存文件块
     *
     * @param chunk
     */
    void saveChunk(Chunk chunk);

    /**
     * 检查文件块是否存在
     *
     * @param identifier
     * @param chunkNumber
     * @return
     */
    boolean checkChunk(String identifier, Integer chunkNumber);
}
