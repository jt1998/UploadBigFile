package com.founder.xmemcached.bean;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @author Mr.jt
 * @create 2022-08-04 17:00
 */

/*文件切片基本信息*/
public class ChunkFileParam implements Serializable {
    /*上传大文件的前缀*/
    private String filePrefix;
    /*上传文件类型*/
    private MultipartFile file;
    /*上传分片文件的总数*/
    private Integer totalParts;
    /*上传大文件的后缀*/
    private String fileSuffix;

    public ChunkFileParam(String filePrefix, MultipartFile file, Integer totalParts,String fileSuffix) {
        this.filePrefix = filePrefix;
        this.file = file;
        this.totalParts = totalParts;
        this.fileSuffix = fileSuffix;
    }

    public ChunkFileParam(String filePrefix, MultipartFile file) {
        this.filePrefix = filePrefix;
        this.file = file;
    }

    public Integer getTotalParts() {
        return totalParts;
    }

    public void setTotalParts(Integer totalParts) {
        this.totalParts = totalParts;
    }

    public ChunkFileParam() {
    }

    public String getFilePrefix() {
        return filePrefix;
    }

    public void setFilePrefix(String filePrefix) {
        this.filePrefix = filePrefix;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    @Override
    public String toString() {
        return "ChunkFileParam{" +
                "filePrefix='" + filePrefix + '\'' +
                ", file=" + file +
                ", totalParts=" + totalParts +
                ", fileSuffix='" + fileSuffix + '\'' +
                '}';
    }
}
