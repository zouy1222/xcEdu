package com.xuecheng.test.fastdfs;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestFastDFS {
    /**
     * 上传
     */
    @Test
    public void upload(){
        try {
            ClientGlobal.initByProperties("fastdfs-client.properties");
            //创建客户端
            TrackerClient client = new TrackerClient();
            //连接tracker server
            TrackerServer server = client.getConnection();
            if (server == null) {
                System.out.println("getConnection return null");
                return;
            }
            //获取一个storage server
            StorageServer ss = client.getStoreStorage(server);
            if (ss == null) {
                System.out.println("getStoreStorage return null");
            }
            //创建一个storage存储客户端
            StorageClient1 sc1 = new StorageClient1(server, ss);
            NameValuePair[] meta_list = null; //new NameValuePair[0];
            String item = "E:\\UpupooResource\\动漫电脑壁纸20181102期\\124578.jpg";
            String fileid;
            fileid = sc1.upload_file1(item, "jpg", meta_list);
            System.out.println("Upload local file " + item + " ok, fileid=" + fileid);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }
    //文件查询
    @Test
    public void testQueryFile() throws IOException, MyException {
        ClientGlobal.initByProperties("fastdfs-client.properties");
        //创建客户端
        TrackerClient client = new TrackerClient();
        //连接tracker server
        TrackerServer server = client.getConnection();
        if (server == null) {
            System.out.println("getConnection return null");
            return;
        }
        StorageServer storageServer = null;
        StorageClient storageClient = new StorageClient(server, storageServer);
        FileInfo fileInfo = storageClient.query_file_info("group1", "M00/00/01/wKhlQFrKBSOAW5AWAALcAg10vf4862.png");
    }

    /**
     * 下载
     */
    @Test
    public void testDownloadFile() throws IOException, MyException {
        ClientGlobal.initByProperties("fastdfs-client.properties");
        //创建客户端
        TrackerClient trackerClient = new TrackerClient();
        //连接tracker server
        TrackerServer trackerServer = trackerClient.getConnection();
        //获取一个storage server
        StorageServer storageServer = null;
        StorageClient1 storageClient1 = new StorageClient1(trackerServer,storageServer);
        byte[] bytes = storageClient1.download_file1("group1/M00/00/01/wKgZmVxRU1iAC7Q8ACmee8dmG_U729.jpg");
        File file = new File("d:\\1.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(bytes);
        fileOutputStream.close();


    }
}
