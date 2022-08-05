package com.founder.xmemcached.runnable;

import com.founder.xmemcached.util.UploadUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author Mr.jt
 * @create 2022-08-05 9:16
 */
public class UploadPartFileRunnable implements Callable<Map<String,Object>> {
    /*定义文件类型*/
    private MultipartFile file;
    /*定义map key*/
    private String fileKey;

    public UploadPartFileRunnable(MultipartFile file, String fileKey) {
        this.file = file;
        this.fileKey = fileKey;
    }


    /**
     *上传单个文件任务
     * @author Mr.jt
     * @date 2022/8/5 9:22
     * @param []
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @Version1.0
    **/
    @Override
    public Map<String, Object> call() throws Exception {
        Map<String,Object> resultMap = new HashMap<>();
        String partFileName = file.getOriginalFilename();
        String partFilePath = UploadUtils.uploadPath + partFileName;
        File partFile = new File(partFilePath);

        if (partFile.exists()){
            resultMap.put("result","FAILURE");
            resultMap.put("msg","文件已经上传过...");
            resultMap.put("fileName",partFileName);
            return resultMap;
        }

        if (file == null || file.isEmpty()){
            resultMap.put("result","FAILURE");
            resultMap.put("msg","文件为空...");
            resultMap.put("fileName",partFileName);
            return resultMap;
        }

        try {
            /*进行文件复制 上传文件夹中*/
            file.transferTo(partFile);
            /*进行添加到partFileNameMap中*/
            boolean containsKey = UploadUtils.partFileNameMap.containsKey(fileKey);
            if (containsKey){
                ArrayList<String> list = UploadUtils.partFileNameMap.get(fileKey);
                list.add(partFilePath);
                UploadUtils.partFileNameMap.put(fileKey,list);
            }else {
                ArrayList<String> list = new ArrayList<>();
                list.add(partFilePath);
                UploadUtils.partFileNameMap.put(fileKey,list);
            }
            resultMap.put("result","SUCCESS");
            resultMap.put("msg","文件上传成功...");
            resultMap.put("fileName",partFileName);
            System.out.println(UploadUtils.partFileNameMap.toString());
            return resultMap;
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("result","FAILURE");
            resultMap.put("msg","文件解析失败....");
            resultMap.put("fileName",partFileName);
            return resultMap;
        }
    }
}
