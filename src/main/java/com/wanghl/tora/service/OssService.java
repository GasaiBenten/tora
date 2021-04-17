package com.wanghl.tora.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author wanghl
 * @date 2021/3/30 - 13:13
 */
public interface OssService {
    Map upload(MultipartFile file);

    void delete(String name);

}
