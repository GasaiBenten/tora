package com.wanghl.tora.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.wanghl.tora.service.OssService;
import com.wanghl.tora.service.ToraBlogService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author wanghl
 * @date 2021/3/30 - 13:13
 */
@Service
public class OssServiceImpl implements OssService {

    @Value("${aliyun.oss.file.endpoint}")
    private String endPoint;
    @Value("${aliyun.oss.file.keyid}")
    private String keyId;
    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;
    @Value("${aliyun.oss.file.bucket}")
    private String bucket;

    @Autowired
    private ToraBlogService blogService;

    @Override
    public Map upload(MultipartFile file) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endPoint, keyId, keySecret);
        try {
            // 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
            InputStream inputStream = file.getInputStream();
            //文件名处理
            String uuid = UUID.randomUUID().toString().replace("-","");
            Integer lastIndex = file.getOriginalFilename().lastIndexOf(".");
            String type = file.getOriginalFilename().substring(lastIndex);
            //String date = new DateTime().toString("yyyy/MM/dd");
            String fileName = uuid + type;
            //上传至oss
            // 填写Bucket名称和Object完整路径。Object完整路径中不能包含Bucket名称。
            ossClient.putObject(bucket, fileName, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();

            //返回url
            String url = "https://"+bucket+"."+endPoint+"/"+fileName;
            Map map = new HashMap();
            map.put("url",url);
            map.put("fileName",fileName);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(String name) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endPoint, keyId, keySecret);

            // 填写Bucket名称和Object完整路径。Object完整路径中不能包含Bucket名称。
            ossClient.deleteObject(bucket, name);
            // 关闭OSSClient。
            ossClient.shutdown();

    }

}
