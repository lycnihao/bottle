package run.bottle.app.model.support;

/**
 * 关于文件公共常量
 *
 * @author lycheng
 * @date 2020/4/18 20:57
 */
public class FileConst {
    /**
     * 用户主目录
     */
    public final static String USER_HOME = System.getProperties().getProperty("user.home");

    /**
     * 临时目录
     */
    public final static String TEMP_DIR = System.getProperties().getProperty("java.io.tmpdir");

    /**
     * 路径分隔符
     */
    public static final String DELIMITER = "/";
}
