package com.founder.xmemcached.util;

import com.sun.org.apache.xml.internal.resolver.Catalog;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Mr.jt
 * @create 2022-08-05 9:07
 */
public class UploadUtils {

    /*定义全局文件下载位置*/
    public final static String uploadPath = "D:/upload/";

    /*key 大文件前缀，value为切片文件名称集合*/
    /*保证可见性*/
   public static volatile Map<String, ArrayList<String>> partFileNameMap = new ConcurrentHashMap<>();



    /**
     *文件切片工具类
     * @author Mr.jt
     * @date 2022/8/5 14:56
     * @param [src, endsrc, num]
     * @return void
     * @Version1.0
    **/
    public static void cutFile(String src, String endsrc, int num) {
       FileInputStream fis = null;
       File file = null;
       try {
           fis = new FileInputStream(src);
           file = new File(src);
           //创建规定大小的byte数组
           byte[] b = new byte[num];
           int len = 0;
           //name为以后的小文件命名做准备
           int name = 1;
           //遍历将大文件读入byte数组中，当byte数组读满后写入对应的小文件中
           while ((len = fis.read(b)) != -1) {
               //分别找到原大文件的文件名和文件类型，为下面的小文件命名做准备
               String name2 = file.getName();
               int lastIndexOf = name2.lastIndexOf(".");
               String substring = name2.substring(0, lastIndexOf);
               String substring2 = name2.substring(lastIndexOf, name2.length());
               FileOutputStream fos = new FileOutputStream(endsrc + "\\\\"+ substring + "-" + name + substring2);
               //将byte数组写入对应的小文件中
               fos.write(b, 0, len);
               //结束资源
               fos.close();
               name++;
           }
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();

       } finally {
           try {
               if (fis != null) {
                   //结束资源
                   fis.close();
               }
           } catch (IOException e) {
               e.printStackTrace();

           }
       }
   }

}
