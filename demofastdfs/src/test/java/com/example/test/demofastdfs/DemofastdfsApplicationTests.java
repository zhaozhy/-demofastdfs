package com.example.test.demofastdfs;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DemofastdfsApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void testUpload() throws Exception{

        ClientGlobal.initByProperties("config/fastdfs-client.properties");
        System.out.println("network_timeout=" + ClientGlobal.g_network_timeout + "ms");
        System.out.println("charset=" + ClientGlobal.g_charset);
        //创建客户端
        TrackerClient tc = new TrackerClient();
        //连接tracker Server
        TrackerServer ts = tc.getConnection();
        if (ts == null) {
            System.out.println("getConnection return null");
            return;
        }
        //获取一个storage server
        StorageServer ss = tc.getStoreStorage(ts);
        if (ss == null) {
            System.out.println("getStoreStorage return null");
        }
        //创建一个storage存储客户端
        StorageClient1 sc1 = new StorageClient1(ts, ss);
        NameValuePair[] meta_list = null; //new NameValuePair[0];
        String item = "D:\\timg.JPG";
        String fileid;
        fileid = sc1.upload_file1(item, "png", meta_list);
        System.out.println("Upload local file " + item + " ok, fileid=" + fileid);
    }


    /**
     * 查询文件
     * @throws Exception
     */
    @Test
    public void testQueryFile() throws Exception{
        ClientGlobal.initByProperties("config/fastdfs-client.properties");
        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = null;
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        FileInfo fileInfo = storageClient.query_file_info("group1",
                "M00/00/00/wKgByFy2ctqAezPaAABNyg2xDQ4309.png");
        System.out.println(fileInfo);
    }

    /**
     * 下载文件
     */
    @Test
    public void testDownloadFile() throws Exception {
        ClientGlobal.initByProperties("config/fastdfs-client.properties");
        TrackerClient tracker = new TrackerClient();
        TrackerServer trackerServer = tracker.getConnection();
        StorageServer storageServer = null;
        StorageClient1 storageClient1 = new StorageClient1(trackerServer,
                storageServer);
        byte[] result = storageClient1.download_file1("group1/M00/00/00/wKgByFy2ctqAezPaAABNyg2xDQ4309.png");
        File file = new File("d:/1.png");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(result);
        fileOutputStream.close();
    }

}
