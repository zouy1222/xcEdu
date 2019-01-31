package com.xuecheng.filesystem.service;

import com.alibaba.fastjson.JSON;
import com.xuecheng.filesystem.dao.FileSystemRepository;
import com.xuecheng.framework.domain.filesystem.FileSystem;
import com.xuecheng.framework.domain.filesystem.response.FileSystemCode;
import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import org.apache.commons.lang3.StringUtils;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 图片上传下载,要手动设置配置信息
 */
@Service
public class FileSystemService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileSystemService.class);
    @Autowired
    private FileSystemRepository fileSystemRepository;
    @Value("${xuecheng.fastdfs.tracker_servers}")
    private String tracker_servers;
    @Value("${xuecheng.fastdfs.connect_timeout_in_seconds}")
    private int connect_timeout_in_seconds;
    @Value("${xuecheng.fastdfs.network_timeout_in_seconds}")
    private int network_timeout_in_seconds;
    @Value("${xuecheng.fastdfs.charset}")
    private String charset;
    /**
     * 加载配置信息
     */
    private void initFdfsConfig(){
        try {
            ClientGlobal.initByTrackers(tracker_servers);
            ClientGlobal.setG_connect_timeout(connect_timeout_in_seconds);
            ClientGlobal.setG_network_timeout(network_timeout_in_seconds);
            ClientGlobal.setG_charset(charset);
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionCast.cast(FileSystemCode.FS_INITFDFSERROR);
        }
    }

    /**
     * 上传文件
     * @param file 这是一个框架内部的一个对象,里面封装了上传文件的信息,和内容
     * @param filetag
     * @param businesskey
     * @param metadata
     * @return
     */
    public UploadFileResult upload(MultipartFile file, String filetag, String businesskey, String metadata){
        if(file == null){
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_FILEISNULL);
        }
        //上传文件到fdfs，返回文件id
        String fileId = this.fdfs_upload(file);
        //创建文件信息对象,进行文件基本信息的封装
        FileSystem fileSystem = new FileSystem();
        //文件id
        fileSystem.setFileId(fileId);
        //文件在文件系统中的路径
        fileSystem.setFilePath(fileId);
        //业务标识
        fileSystem.setBusinesskey(businesskey);
        //标签
        fileSystem.setFiletag(filetag);
        //元数据
        if(StringUtils.isNotEmpty(metadata)){
            try {
                Map map = JSON.parseObject(metadata, Map.class);
                fileSystem.setMetadata(map);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //名称
        fileSystem.setFileName(file.getOriginalFilename());
        //大小
        fileSystem.setFileSize(file.getSize());
        //文件类型
        fileSystem.setFileType(file.getContentType());
        fileSystemRepository.save(fileSystem);
        return new UploadFileResult(CommonCode.SUCCESS,fileSystem);
    }

    /**
     *上传文件到fdfs，返回文件id
     * @param file
     * @return
     */
    private String fdfs_upload(MultipartFile file){

        try {
            //加载fastDFS
            this.initFdfsConfig();
            //创建tracker client
            TrackerClient trackerClient = new TrackerClient();
            //获取trackerService
            TrackerServer trackerServer = trackerClient.getConnection();
            //获取storage
            StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
            //获取storageClient
            StorageClient1 storageClient1 = new StorageClient1(trackerServer,storageServer);
            //上传文件
            //文件字节
            byte[] bytes = file.getBytes();
            //文件原始名称
            String originalFilename = file.getOriginalFilename();
            String extname = originalFilename.substring(originalFilename.indexOf(".") + 1);
            //上传文件获取文件id
            String fileId = storageClient1.upload_file1(bytes, extname, null);
            return fileId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
