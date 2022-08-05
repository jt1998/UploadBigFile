package com.founder.xmemcached.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.founder.xmemcached.bean.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.jt
 * @create 2022-08-02 15:05
 */
public class ExcelListener extends AnalysisEventListener<Student> {

    public static  List<Student> students = new ArrayList<>();

    /*一行行读取excel中的数据 不含表头数据*/
    @Override
    public void invoke(Student student, AnalysisContext analysisContext) {
        students.add(student);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("excel数据读取完成.....");
    }
}
