package run.bottle.app.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Path;
import java.security.MessageDigest;
import org.springframework.util.Assert;

/**
 * 文件哈希值
 *
 * @author LiYuanCheng
 * @date 2020-05-08 10:51
 */
public class FileHashCode {

  public static String md5HashCode(String filePath) {
    System.out.println(filePath);
    FileInputStream fis = null;
    try {
      fis = new FileInputStream(filePath);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return md5HashCode(fis);
  }

  /**
   * java获取文件的md5值
   * @param fis 输入流
   * @return
   */
  public static String md5HashCode(InputStream fis) {
    Assert.notNull(fis,"文件读取错误");
    try {
      //拿到一个MD5转换器,如果想使用SHA-1或SHA-256，则传入SHA-1,SHA-256
      MessageDigest md = MessageDigest.getInstance("MD5");

      //分多次将一个文件读入，对于大型文件而言，比较推荐这种方式，占用内存比较少。
      byte[] buffer = new byte[1024];
      int length = -1;
      while ((length = fis.read(buffer, 0, 1024)) != -1) {
        md.update(buffer, 0, length);
      }
      fis.close();
      //转换并返回包含16个元素字节数组,返回数值范围为-128到127
      byte[] md5Bytes  = md.digest();
      //1代表绝对值
      BigInteger bigInt = new BigInteger(1, md5Bytes);
      //转换为16进制
      return bigInt.toString(16);
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }

  public static void main(String[] args) {
    try {
      String path = "C:\\Users\\admin\\Pictures\\011.jpg";
      String md5 = md5HashCode(new FileInputStream(path));
      System.out.println(md5);
      System.out.println(md5.toUpperCase());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}
