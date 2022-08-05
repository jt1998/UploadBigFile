package com.founder.xmemcached.controller;

import com.alibaba.excel.EasyExcel;
import com.founder.xmemcached.bean.Student;
import com.founder.xmemcached.listener.ExcelListener;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * @author Mr.jt
 * @create 2022-08-02 14:20
 */
@RestController
@RequestMapping("/EasyExcel")
public class EasyExcelController {
    private int count = 0;
    /**
     *
     * @author Mr.jt
     * @date 2022/8/3 14:49
     * @param [file]
     * @return java.lang.String
     * @Version1.0
    **/
//    @PostMapping("/upload1")
//    public String uploadFile(@RequestParam("file") MultipartFile file){
//        count++;
//        ConcurrentHashMap<String,Object> map = new ConcurrentHashMap<String,Object>(10);
//        String fileName = file.getOriginalFilename();
//        String pathPrefix = "D:/upload"+count+"/";
//        try {
//            FileUtils.copyInputStreamToFile(file.getInputStream(),new File(pathPrefix+fileName));
////            ExcelListener listener = new ExcelListener();
////            File finalFile = new File(pathPrefix + fileName);
////            EasyExcel.read(finalFile,Student.class,listener).sheet().doRead();
////            System.out.println(ExcelListener.students.toString());
////            boolean delete = finalFile.delete();
////            System.out.println(delete);
//            return "文件上传成功";
//        }catch (Exception e){
//            e.printStackTrace();
//            return "文件上传失败";
//        }
//
//
//    }


    @PostMapping("/download")
    public String download(HttpServletResponse response){
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        try {
            /*防止文件名称乱码*/
            String fileName= URLEncoder.encode("学生信息","UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition","attachment;filename*=utf-8''"+fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(),Student.class).sheet("学生信息").doWrite(getStudentList());
            return "文件下载成功";
        }catch (Exception e){
            return "文件下载异常";
        }
    }



    public List<Student> getStudentList(){
        List<Student> studentList = new ArrayList<Student>();
        Student student = new Student();
        student.setName("姜涛");
        student.setAddress("河南省信阳市淮滨县");
        student.setAge(24);
        student.setGender("男");
        student.setHeight(180);
        studentList.add(student);
        return studentList;
    }


    /**
     *
     * @author Mr.jt
     * @date 2022/8/4 16:20
     * @param [file]
     * @return java.lang.String
     * @Version1.0
    **/
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file){
        count++;
        long start = System.currentTimeMillis();

        String fileName = file.getOriginalFilename();
        String pathPrefix = "D:/upload"+count+"/";
        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(),new File(pathPrefix+fileName));
            long end = System.currentTimeMillis();
            System.out.println("本地文件上传耗时：" + (end-start)/1000 + "秒");
            return "文件上传成功";
        }catch (Exception e){
            e.printStackTrace();
            return "文件上传失败";
        }


    }



}
