package com.atguigu.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: litie
 * @Date: 2021/11/24/16:34
 */

//一级分类
@Data
public class oneSubject {

    private String id;

    private String title;

    //一个一级分类有多个二级分类
    private List<twoSubject> children = new ArrayList<>();

}
