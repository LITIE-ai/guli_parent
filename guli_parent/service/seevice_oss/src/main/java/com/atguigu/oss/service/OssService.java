package com.atguigu.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: litie
 * @Date: 2021/11/15/11:23
 */
public interface OssService {
    String uploadFileAvatar(MultipartFile file);
}
