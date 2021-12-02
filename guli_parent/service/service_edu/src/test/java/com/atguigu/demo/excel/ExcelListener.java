package com.atguigu.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: litie
 * @Date: 2021/11/22/10:03
 */
public class ExcelListener extends AnalysisEventListener<DemoData> {
    //一行一行读取excel内容
    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
        System.out.println("dddd" + demoData);
    }

    @Override
    public void invokeHeadMap(Map<Integer,String> headMap, AnalysisContext context) {
        /* compiled code */
        System.out.println("表头：" + headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
