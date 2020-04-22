package run.bottle.app.utils;

import run.bottle.app.exception.ServiceException;
import run.bottle.app.model.dto.FileItemDTO;
import run.bottle.app.model.dto.FolderNode;
import run.bottle.app.model.support.FileConst;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import static run.bottle.app.model.support.FileConst.DELIMITER;

/**
 * @author: lycheng
 * @date: 2020/4/19 11:06
 */
public class FileUtils {

    private static final String workDir = FileConst.USER_HOME + DELIMITER + ".bottle" + DELIMITER + "upload/";

    public static List<FolderNode> getAllFolderNode(){
        return getFolderNode("root");
    }

    public static List<FolderNode> getFolderNode(String path){
        List<FolderNode> folderNodes = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(workDir,path))) {

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
}
