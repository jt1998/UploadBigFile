package com.founder.xmemcached.runnable;

import com.founder.xmemcached.bean.ChunkFileParam;
import com.founder.xmemcached.util.UploadUtils;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

/**
 * @author Mr.jt
 * @create 2022-08-05 10:09
 */
public class FileMegerRunnable implements Callable<Map<String,Object>> {

  private String fileSuffix;
  private String filePrefix;

    public FileMegerRunnable(String filePrefix,String fileSuffix) {
        this.filePrefix = filePrefix;
        this.fileSuffix = fileSuffix;
    }

    @Override
    public Map<String, Object> call() throws Exception {
        Map<String,Object> resultMap = new HashMap<>();
        File resultFile = new File(UploadUtils.uploadPath + filePrefix + "." + fileSuffix);
        if (resultFile.exists()){
            resultMap.put("result","SUCCESS");
            resultMap.put("msg","文件已经存在...");
            resultMap.put("fileName",filePrefix + "." + fileSuffix);
            return resultMap;
        }

        boolean containsKey = UploadUtils.partFileNameMap.containsKey(filePrefix);
        if (containsKey){
            ArrayList<String> list = UploadUtils.partFileNameMap.get(filePrefix);
            /*进行name排序 防止内容不一致*/
            List<String> newList = list.stream().sorted().collect(Collectors.toList());

            System.out.println(newList.toString());

            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(resultFile));
            byte[] buffer =  new byte[1024*1024*100];
            for (String name : newList) {
                /*进行文件合并*/
                File partFile =  new File(name);
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(partFile));
                int read;
                while ((read = bis.read(buffer)) != -1){
                    bos.write(buffer,0,read);
                }
                /*关流*/
                bis.close();
                /*删除切片文件*/
                partFile.delete();
            }

            /*关流*/
            bos.close();
            resultMap.put("result","SUCCESS");
            resultMap.put("msg","文件合并成功.....");
            resultMap.put("fileName",filePrefix + "." + fileSuffix);
            return resultMap;
        }else {
            resultMap.put("result","FAILURE");
            resultMap.put("msg","该文件没有进行上传...");
            resultMap.put("fileName",filePrefix + "." + fileSuffix);
            return resultMap;
        }
    }


}
