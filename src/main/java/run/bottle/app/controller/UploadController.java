package run.bottle.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import run.bottle.app.model.entity.Chunk;
import run.bottle.app.model.entity.FileInfo;
import run.bottle.app.model.params.MergeFileParam;
import run.bottle.app.service.ChunkService;
import run.bottle.app.service.FileInfoService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static run.bottle.app.model.support.FileConst.DELIMITER;
import static run.bottle.app.utils.FileUtils.generatePath;
import static run.bottle.app.utils.FileUtils.getWorkDir;
import static run.bottle.app.utils.FileUtils.merge;
import static run.bottle.app.utils.FileUtils.specialName;


@RestController
@RequestMapping("api/admin/uploader")
@Slf4j
public class UploadController {

    @Resource
    private FileInfoService fileInfoService;
    @Resource
    private ChunkService chunkService;

    /**
     * 切块上传
     * @param chunk
     * @return
     */
    @PostMapping("/chunk")
    public String uploadChunk(Chunk chunk) {
        MultipartFile file = chunk.getFile();
        log.debug("file originName: {}, chunkNumber: {}", file.getOriginalFilename(), chunk.getChunkNumber());

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(generatePath(getWorkDir(), chunk));
            //文件写入指定路径
            Files.write(path, bytes);
            log.debug("文件 {} 写入成功, uuid:{}", chunk.getFilename(), chunk.getIdentifier());
            chunkService.saveChunk(chunk);

            return "文件上传成功";
        } catch (IOException e) {
            e.printStackTrace();
            return "后端异常...";
        }
    }

    /**
     * 验证文件
     * @param chunk
     * @param response
     * @return
     */
    @GetMapping("/chunk")
    public Object checkChunk(Chunk chunk, HttpServletResponse response) {
        if (chunkService.checkChunk(chunk.getIdentifier(), chunk.getChunkNumber())) {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
        }

        return chunk;
    }

    /**
     * 合并
     * @param mergeFileParam
     * @return
     */
    @PostMapping("/mergeFile")
    public String mergeFile(@RequestBody MergeFileParam mergeFileParam) {
        String filename = specialName(mergeFileParam.getFileName());
        String file = getWorkDir() + mergeFileParam.getPath() + DELIMITER + filename;
        String folder = getWorkDir() + "/" + mergeFileParam.getIdentifier();
        merge(file, folder, filename);
        mergeFileParam.setLocation(file);
        FileInfo fileInfo = new FileInfo();
        BeanUtils.copyProperties(fileInfo,mergeFileParam);
        fileInfoService.addFileInfo(fileInfo);

        return "合并成功";
    }
}
