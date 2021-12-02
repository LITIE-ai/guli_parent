package com.atguigu.oss.service.Impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.InputStream;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: litie
 * @Date: 2021/11/15/11:23
 */
@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        String endpoint = ConstantPropertiesUtils.END_POIND;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try {
            OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);

            String fileName = file.getOriginalFilename();

            //在文件名称里面添加随机唯一的值
            String s = UUID.randomUUID().toString().replaceAll("-","");
            fileName = s + fileName;

            //把文件按日期进行分类
            String datePath = new DateTime().toString("yyyy/MM/dd");
            fileName = datePath + "/" +fileName;


            InputStream inputStream = file.getInputStream();

            ossClient.putObject(bucketName,fileName,inputStream);

            ossClient.shutdown();

            //https://edu-litie.oss-cn-beijing.aliyuncs.com/image.jpg
            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;

            return url;

        }catch (Exception e){
            e.printStackTrace();
        }


        return null;
    }
}
