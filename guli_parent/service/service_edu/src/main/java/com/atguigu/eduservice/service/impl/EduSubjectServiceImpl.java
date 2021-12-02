package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.oneSubject;
import com.atguigu.eduservice.entity.subject.twoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-11-22
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService eduSubjectService) {
        try {
            //文件输入流
            InputStream in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //课程分类列表（树形）
    @Override
    public List<oneSubject> getAllOneTwoSubject() {
        //一级查询
        QueryWrapper<EduSubject> wrapperone = new QueryWrapper<>();
        wrapperone.eq("parent_id","0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperone);

        //二级查询
        QueryWrapper<EduSubject> wrappertwo = new QueryWrapper<>();
        wrappertwo.ne("parent_id","0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrappertwo);

        //创建list集合，用于存储最终封装数据
        List<oneSubject> finalSubjectList = new ArrayList<>();

        //封装一级分类
        //查询出来所有的一级分类list集合遍历，得到每一个一级分类对象，获取每一个一级分类对象
        //封装到要求的list集合里面
        for (int i = 0; i < oneSubjectList.size(); i++) {
            //得到onesubjectlist里面的每个edusubject对象
            EduSubject eduSubject = oneSubjectList.get(i);
            oneSubject oneSubject = new oneSubject();
            BeanUtils.copyProperties(eduSubject,oneSubject);

            finalSubjectList.add(oneSubject);

            //在一级分类循环遍历所有的二级分类
            //创建list集合封装每个一级分类中的二级分类
            List<twoSubject> twoFinalSubjectList = new ArrayList<>();

            //遍历二级分类list集合
            for (int i1 = 0; i1 < twoSubjectList.size(); i1++) {
                EduSubject tSubject = twoSubjectList.get(i1);
                if(tSubject.getParentId().equals(eduSubject.getId())){
                    twoSubject twoSubject = new twoSubject();
                    BeanUtils.copyProperties(tSubject,twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            //把一级分类下面的所有二级分类放到一级分类中
            oneSubject.setChildren(twoFinalSubjectList);
        }
        return finalSubjectList;
    }
}
