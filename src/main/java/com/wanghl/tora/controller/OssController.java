package com.wanghl.tora.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.wanghl.tora.result.Result;
import com.wanghl.tora.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author wanghl
 * @date 2021/3/30 - 13:13
 */
@RestController
@RequestMapping("/oss/file")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping("upload")
    public Result upload(MultipartFile file){
        Map map = ossService.upload(file);
        return Result.ok().data(map);
    }

    @PostMapping("delete/{name}")
    public Result delete(@PathVariable String name){
        ossService.delete(name);
        return Result.ok();
    }

    public static void main(String[] args) {
        String str = "http://seikai-tora-blog.oss-cn-beijing.aliyuncs.com/af3602851e9f48199b2837af2d2c609e.png";
        System.out.println(str.substring(52));
    }

}
