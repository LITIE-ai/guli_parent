package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-11-25
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    //注入小节的service
    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

        //根据课程id查询课程里面的所有章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<EduChapter> eduChapterlist = baseMapper.selectList(wrapperChapter);

        //根据课程id查询课程里面的所有小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> eduVideolist = eduVideoService.list(wrapperVideo);

        //创建list集合 用于最终封装数据
        List<ChapterVo> finalList = new ArrayList<>();

        //遍历查询章节list集合进行封装
        //遍历查询章节list集合
        for (int i = 0; i < eduChapterlist.size(); i++) {
            EduChapter eduChapter = eduChapterlist.get(i);
            //educhapter对象复制到chaptervo里面
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            finalList.add(chapterVo);

            List<VideoVo> videoList = new ArrayList<>();

            for (int i1 = 0; i1 < eduVideolist.size(); i1++) {
                EduVideo eduVideo = eduVideolist.get(i1);
                if(eduVideo.getChapterId().equals(eduChapter.getId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    videoList.add(videoVo);
                }
            }
            //把封装之后的小节放到章节里里面
            chapterVo.setChildren(videoList);
        }
        return finalList;
    }

    //根据id删除章节
    @Override
    public Boolean deleteChapter(String chapterId) {
        //如果章节下面有小节，不能删除，
        //如果没有，可以删除
        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("chapter_Id",chapterId);
        //如果count为0，表示未查询到小节数据，则可以删除章节
        //如果count不为0，表示查询到小节数据，不能删除章节
        int count = eduVideoService.count(eduVideoQueryWrapper);
        if(count > 0){
            throw new GuliException(20001,"此章节含有小节内容，不能删除章节内容");
        }
        int result = baseMapper.deleteById(chapterId);

        return result > 0;
    }
}
