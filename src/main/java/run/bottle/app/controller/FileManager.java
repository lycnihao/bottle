package run.bottle.app.controller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import run.bottle.app.exception.ServiceException;
import run.bottle.app.model.dto.FileItemDTO;
import run.bottle.app.model.dto.FolderNode;
import run.bottle.app.model.support.BaseResponse;
import run.bottle.app.model.support.FileConst;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 文件管理
 *
 * @author lycheng
 * @date 2020/4/18 20:57
 */
@RestController
@RequestMapping(value = "api/admin/fileManager")
public class FileManager {

    private final String workDir;

    public FileManager() {
        workDir = FileConst.USER_HOME + FileConst.DELIMITER + ".bottle" + FileConst.DELIMITER + "upload/";
    }

    /**
     * 展示文件列表
     */
    @RequestMapping("list")
    public BaseResponse list(String path) throws ServiceException {

        // 返回的结果集
        List<FileItemDTO> fileItems = new ArrayList<>();

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(workDir ,path))) {

            String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat dt = new SimpleDateFormat(DATE_FORMAT);
            for (Path pathObj : directoryStream) {
                // 获取文件基本属性
                BasicFileAttributes attrs = Files.readAttributes(pathObj, BasicFileAttributes.class);
                String mediaType = Files.probeContentType(pathObj);

                FileItemDTO fileItem = new FileItemDTO();
                fileItem.setName(pathObj.getFileName().toString());
                fileItem.setDate(dt.format(new Date(attrs.lastModifiedTime().toMillis())));
                fileItem.setSize(attrs.size());
                fileItem.setIsDirectory(attrs.isDirectory());
                fileItem.setMediaType( attrs.isDirectory() || StringUtils.isEmpty(mediaType) ? null : MediaType.valueOf(mediaType).toString());
                fileItems.add(fileItem);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException("文件获取异常");
        }
        return BaseResponse.ok(fileItems.stream().sorted(Comparator.comparing(FileItemDTO::getIsDirectory).reversed()));
    }

    /**
     * 文件上传
     */
    @RequestMapping("upload")
    public FileItemDTO upload(@RequestPart("file") MultipartFile file, @RequestParam(value = "path", defaultValue = "root") String destination) {
        Path path = Paths.get(workDir, destination);
        // 上传文件
        try {
            String fileName = file.getOriginalFilename();
            String prefix = "";
            if (Objects.requireNonNull(file.getOriginalFilename()).contains("/")){
                prefix = run.bottle.app.utils.FileUtils.prefixName(file.getOriginalFilename());
                fileName = run.bottle.app.utils.FileUtils.suffixName(file.getOriginalFilename());
                Files.createDirectories(Paths.get(path.toString(), FileConst.DELIMITER, prefix));
                System.out.println(Paths.get(path.toString(), FileConst.DELIMITER, prefix));
            }

            Files.createFile(Paths.get(path.toString(), FileConst.DELIMITER ,prefix + fileName));
            file.transferTo(Paths.get(path.toString(), FileConst.DELIMITER , prefix + fileName));
            FileItemDTO fileItemDTO = new FileItemDTO();
            fileItemDTO.setName(file.getOriginalFilename());
            fileItemDTO.setMediaType(MediaType.valueOf(Objects.requireNonNull(file.getContentType())).toString());
            fileItemDTO.setSize(file.getSize());
            fileItemDTO.setIsDirectory(false);
            return fileItemDTO;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 创建目录
     */
    @RequestMapping("createFolder")
    public BaseResponse createFolder(@RequestParam(value = "path") String newPath) {
        try {
            File newDir = new File(workDir + newPath);
            System.out.println("创建文件夹-->" + newDir);
            if (!newDir.mkdir()) {
                throw new Exception("不能创建目录: " + newPath);
            }
            return BaseResponse.ok("创建成功");
        } catch (Exception e) {
            throw new ServiceException("创建文件夹失败").setErrorData(e);
        }
    }

    /**
     * 重命名文件或目录
     */
    @RequestMapping("rename")
    public BaseResponse rename(@RequestParam(value = "path") String path, @RequestParam(value = "newPath") String newPath) {
        try {

            File srcFile = new File(workDir, path);
            File destFile = new File(workDir, newPath);
            if (srcFile.isFile()) {
                FileUtils.moveFile(srcFile, destFile);
            } else {
                FileUtils.moveDirectory(srcFile, destFile);
            }
            return BaseResponse.ok("保存成功");
        } catch (Exception e) {
            throw new ServiceException("保存失败").setErrorData(e);
        }
    }

    /**
     * 复制文件或目录
     */
    @RequestMapping("copy")
    public Object copy(@RequestParam(value = "paths") String paths, @RequestParam(value = "newPath") String newPath) {
        try {
            String[] items = paths.split(",");

            for (int i = 0; i < items.length; i++) {
                String path = items[i];

                File srcFile = new File(workDir, path);
                File destFile = new File(workDir + newPath, srcFile.getName());

                FileCopyUtils.copy(srcFile, destFile);
            }
            return BaseResponse.ok("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("保存失败").setErrorData(e);
        }
    }


    /**
     * 移动文件或目录
     */
    @RequestMapping("move")
    public Object move(@RequestParam(value = "paths") String paths, @RequestParam(value = "newPath") String newPath) {
        try {
            String[] items = paths.split(",");

            for (int i = 0; i < items.length; i++) {
                String path = items[i];

                File srcFile = new File(workDir, path);
                File destFile = new File(workDir + newPath, srcFile.getName());

                if (srcFile.isFile()) {
                    FileUtils.moveFile(srcFile, destFile);
                } else {
                    FileUtils.moveDirectory(srcFile, destFile);
                }
            }
            return BaseResponse.ok("保存成功");
        } catch (Exception e) {
            throw new ServiceException("保存失败").setErrorData(e);
        }
    }

    /**
     * 删除文件或目录
     */
    @RequestMapping("remove")
    public Object remove(@RequestParam(value = "paths") String paths) {
        try {
            String[] items = paths.split(",");
            for (int i = 0; i < items.length; i++) {
                String path = items[i];
                File srcFile = new File(workDir, path);
                if (!FileUtils.deleteQuietly(srcFile)) {
                    throw new ServiceException("删除失败: " + srcFile.getAbsolutePath());
                }
            }
            return BaseResponse.ok("删除成功");
        } catch (Exception e) {
            throw new ServiceException("删除失败").setErrorData(e);
        }
    }

    @GetMapping("folderNode")
    private List<FolderNode> getFolderNode(){
        return run.bottle.app.utils.FileUtils.getAllFolderNode();
    }
}
