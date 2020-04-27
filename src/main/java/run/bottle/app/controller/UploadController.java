package run.bottle.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import run.bottle.app.model.entity.Chunk;
import run.bottle.app.model.entity.FileInfo;
import run.bottle.app.service.ChunkService;
import run.bottle.app.service.FileInfoService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static run.bottle.app.utils.FileUtils.generatePath;
import static run.bottle.app.utils.FileUtils.merge;



@RestController
@RequestMapping("api/admin/uploader")
@Slf4j
public class UploadController {

    @Value("${admin.upload-folder}")
    private String uploadFolder;

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
            Path path = Paths.get(generatePath(uploadFolder, chunk));
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
     * @param fileInfo
     * @return
     */
    @PostMapping("/mergeFile")
    public String mergeFile(@RequestBody FileInfo fileInfo) {
        String filename = fileInfo.getFilename();
        String file = uploadFolder + "/" + fileInfo.getIdentifier() + "/" + filename;
        String folder = uploadFolder + "/" + fileInfo.getIdentifier();
        merge(file, folder, filename);
        fileInfo.setLocation(file);
        fileInfoService.addFileInfo(fileInfo);

        return "合并成功";
    }
}
