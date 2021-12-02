package com.atguigu.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: litie
 * @Date: 2021/11/22/9:46
 */
public class TestEasyExcel {
    public static void main(String[] args) {
        //实现excel写操作
        //1.设置写入文件夹地址和excel文件名称
       // String filename = "D:\\desktop\\write.xlsx";

        //2.调用easyexcel中的方法实现写操作
        //EasyExcel.write(filename,DemoData.class).sheet("学生列表").doWrite(getData());


        //实现excel读操作
        String filename = "D:\\desktop\\write.xlsx";
        EasyExcel.read(filename,DemoData.class,new ExcelListener()).sheet().doRead();
    }

    //创建方法返回list集合
    private  static List<DemoData> getData(){
        List<DemoData> list = new ArrayList<>();
        for (int i=0; i<10; i++){
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("dd" + i);
            list.add(data);
        }
         return list;
    }
}
