package com.founder.xmemcached.controller;

import com.founder.xmemcached.bean.ChunkFileParam;
import com.founder.xmemcached.runnable.FileMegerRunnable;
import com.founder.xmemcached.runnable.UploadPartFileRunnable;
import com.founder.xmemcached.util.UploadUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


/**
 * @author Mr.jt
 * @create 2022-08-04 16:59
 */
@RestController
@RequestMapping("/ChunkFileUpload")
public class ChunkFileUpload {


    /**
     * 文件切片上传
     * @param [chunkFileParam] file、文件前缀
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author Mr.jt
     * @date 2022/8/5 10:17
     * @Version1.0
     **/
    @PostMapping("/upload")
    public Map<String, Object> upload(MultipartFile file, String filePrefix) {
        Math.random();
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        UploadPartFileRunnable uploadPartFileRunnable = new UploadPartFileRunnable(file, filePrefix);
        Map<String, Object> resultMap = null;
        try {
            resultMap = threadPool.submit(uploadPartFileRunnable).get();
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("result", "FAILURE");
            resultMap.put("msg", "切片文件多线程失败....");
            resultMap.put("fileName", file.getOriginalFilename());
        }
        return resultMap;
    }


    /**
     * 查看文件已经切片进度进度
     * @param [chunkFileParam] 文件前缀、文件切片总数
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author Mr.jt
     * @date 2022/8/5 10:16
     * @Version1.0
     **/
    @GetMapping("/getFileCultStatus")
    public Map<String, Object> getFileUploadStatus(String filePrefix, Integer totalParts) {
        Map<String, Object> resultMap = new HashMap<>();
        boolean containsKey = UploadUtils.partFileNameMap.containsKey(filePrefix);
        if (!containsKey) {
            resultMap.put("result", "FAILURE");
            resultMap.put("msg", "该文件没有上传......");
            resultMap.put("data", "0%");
            return resultMap;
        } else {
            ArrayList<String> nameList = UploadUtils.partFileNameMap.get(filePrefix);
            int size = nameList.size();
            double num = new BigDecimal((float) size / totalParts).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
            NumberFormat numberFormat = NumberFormat.getPercentInstance();
            numberFormat.setMaximumFractionDigits(4);
            String resultFormat = numberFormat.format(num);
            resultMap.put("result", "SUCCESS");
            resultMap.put("msg", "查询切片进度成功.....");
            resultMap.put("data", resultFormat);
            return resultMap;
        }
    }

    @GetMapping("/getFileBindSatus")
    public Map<String,Object> getFileBindSatus(String filePrefix, Integer totalParts) {
        Map<String, Object> resultMap = new HashMap<>();
        boolean containsKey = UploadUtils.partFileNameMap.containsKey(filePrefix);
        if (!containsKey) {
            resultMap.put("result", "FAILURE");
            resultMap.put("msg", "该文件没有上传......");
            resultMap.put("data", "0%");
            return resultMap;
        } else {
            ArrayList<String> nameList = UploadUtils.partFileNameMap.get(filePrefix);
            int size = nameList.size();
            double num = (double) 1 - new BigDecimal((float) size / totalParts).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
            NumberFormat numberFormat = NumberFormat.getPercentInstance();
            numberFormat.setMaximumFractionDigits(4);
            String resultFormat = numberFormat.format(num);
            resultMap.put("result", "SUCCESS");
            resultMap.put("msg", "查询合并进度成功.....");
            resultMap.put("data", resultFormat);
            return resultMap;
        }
    }



        /**
         * @param [chunkFileParam] 文件前缀、文件后缀
         * @return java.util.Map<java.lang.String, java.lang.Object>
         * @author Mr.jt
         * @date 2022/8/5 10:16
         * @Version1.0
         * 文件合并回调方法
         **/
    @GetMapping("/fileMeger")
    public Map<String, Object> fileMeger(String filePrefix, String fileSuffix) {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        FileMegerRunnable fileMegerRunnable = new FileMegerRunnable(filePrefix, fileSuffix);
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap = threadPool.submit(fileMegerRunnable).get();
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("result", "FAILURE");
            resultMap.put("msg", "合并文件多线程失败....");
            resultMap.put("fileName", filePrefix + "." + fileSuffix);


        }
        return resultMap;
    }



    @GetMapping("/deleFileKey")
    public Map<String,Object> deleFileKey(String filePrefix){
        Map<String,Object> resultMap = new HashMap<>();
        boolean containsKey = UploadUtils.partFileNameMap.containsKey(filePrefix);
        if (containsKey){
            UploadUtils.partFileNameMap.remove(filePrefix);
            resultMap.put("result", "SUCCESS");
            resultMap.put("msg", "删除key值成功....");
            return resultMap;
        }else {
            resultMap.put("result", "FAILURE");
            resultMap.put("msg", "删除key值失败....");
            return resultMap;
        }
    }

    public static void main(String[] args) {
    }


}
