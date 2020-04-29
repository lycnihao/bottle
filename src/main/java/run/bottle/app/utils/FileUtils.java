package run.bottle.app.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import run.bottle.app.exception.ServiceException;
import run.bottle.app.model.dto.FolderNode;
import run.bottle.app.model.entity.Chunk;
import run.bottle.app.model.support.FileConst;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import static run.bottle.app.model.support.FileConst.DELIMITER;

/**
 * @author: lycheng
 * @date: 2020/4/19 11:06
 */
@Slf4j
@Component
public class FileUtils {

    private static String workDir;

    public FileUtils(Environment environment) {
        workDir = environment.getProperty("admin.upload-folder");
    }

    public static String getWorkDir() {
        if (workDir == null) {
            return FileConst.USER_HOME + DELIMITER + ".bottle" + DELIMITER + "upload/";
        }
        return workDir;
    }

    public static List<FolderNode> getAllFolderNode(){
        return getFolderNode("root");
    }

    public static List<FolderNode> getFolderNode(String path){
        List<FolderNode> folderNodes = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(getWorkDir(),path))) {

            for (Path pathObj : directoryStream) {
                // 获取文件基本属性
                BasicFileAttributes attrs = Files.readAttributes(pathObj, BasicFileAttributes.class);
                if (attrs.isDirectory()){
                    FolderNode folderNode = new FolderNode();
                    folderNode.setKey(path + DELIMITER + pathObj.getFileName().toString());
                    folderNode.setName(pathObj.getFileName().toString());
                    folderNode.setDisabled(false);
                    folderNode.setChild(getFolderNode(path + DELIMITER + folderNode.getName()));
                    folderNodes.add(folderNode);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException("文件节点获取异常");
        }
        return folderNodes;
    }

    public static String prefixName(String name){
        return prefix(name, "/");
    }

    public static String suffixName(String name){
        return suffix(name, "/");
    }

    private static String prefix(String name,String regex){
        int index = name.lastIndexOf("/");
        if (index == -1){
            return null;
        }
        return name.substring(0,index);
    }

    private static String suffix(String name,String regex){
        int index = name.lastIndexOf("/");
        if (index == -1){
            return null;
        }
        return name.substring(index);
    }

    public static Path generatePath(Path path) {
        //判断路径是否存在，不存在则创建
        if (!Files.isWritable(path)) {
            log.info("path not exist,create path: {}", path);
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }

        return path;
    }

    public static String generatePath(String uploadFolder, Chunk chunk) {
        StringBuilder sb = new StringBuilder();
        sb.append(uploadFolder).append("/").append(chunk.getIdentifier());
        //判断uploadFolder/identifier 路径是否存在，不存在则创建
        if (!Files.isWritable(Paths.get(sb.toString()))) {
            log.info("path not exist,create path: {}", sb.toString());
            try {
                Files.createDirectories(Paths.get(sb.toString()));
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }

        return sb.append("/")
                .append(chunk.getFilename())
                .append("-")
                .append(chunk.getChunkNumber()).toString();
    }

    public static String specialName(String name){
        return name;
    }
    /**
     * 文件合并
     *
     * @param targetFile
     * @param folder
     */
    public static void merge(String targetFile, String folder, String filename) {
        try {
            Files.createFile(Paths.get(targetFile));
            Files.list(FileUtils.generatePath(Paths.get(folder)))
                    .filter(path -> !path.getFileName().toString().equals(filename))
                    .sorted((o1, o2) -> {
                        String p1 = o1.getFileName().toString();
                        String p2 = o2.getFileName().toString();
                        int i1 = p1.lastIndexOf("-");
                        int i2 = p2.lastIndexOf("-");
                        return Integer.valueOf(p2.substring(i2)).compareTo(Integer.valueOf(p1.substring(i1)));
                    })
                    .forEach(path -> {
                        try {
                            //以追加的形式写入文件
                            Files.write(Paths.get(targetFile), Files.readAllBytes(path), StandardOpenOption.APPEND);
                            //合并后删除该块
                            Files.delete(path);
                        } catch (IOException e) {
                            log.error(e.getMessage(), e);
                        }
                    });
          Files.delete(Paths.get(folder));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
