package com.xuecheng.api.filesystem;

import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import io.swagger.annotations.Api;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "图片管理接口",description = "图片管理接口,提供图片的上传下载")
public interface FileSystemControllerApi {
    /**
     * 图片上传
     * @param multipartFile 文件
     * @param filetag 文件标签
     * @param businesskey   业务key
     * @param metadata 元信息,json格式
     * @return
     */
    UploadFileResult upload(MultipartFile multipartFile, String filetag, String businesskey, String metadata);
}
