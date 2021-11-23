package com.example.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @Author zhk
 * @Date 2021/11/22 18:02
 * @Version 1.0
 **/

@RestController
@RequestMapping("/file")
public class FileController {

    @PostMapping(value = "/upload")
    String uploadFileBufferToLocal(MultipartFile file) {

        //将文件缓冲到本地
        //windows下的文件路径
        String filePath = "E:/upload/test";
        boolean localFile = createLocalFile(filePath, file);
        if(!localFile){
            return "Create local file failed!";
        }
        return "Create local file successfully";
    }

    /**
     * 通过上传的文件名，缓冲到本地，后面才能解压、验证
     * @param filePath 临时缓冲到本地的目录
     * @param file
     */
    public boolean createLocalFile(String filePath,MultipartFile file) {
        File localFile = new File(filePath);
        //先创建目录
        boolean mkdirs = localFile.mkdirs();

        String originalFilename = file.getOriginalFilename();
        String path = filePath+"/"+originalFilename;

        //log.info("createLocalFile path = {}", path);

        localFile = new File(path);
        FileOutputStream fos = null;
        InputStream in = null;
        if(mkdirs){
            try {
                if(localFile.exists()){
                    //如果文件存在删除文件
                    boolean delete = localFile.delete();
                    if (!delete){
                        System.out.println("--error");
                        //log.error("Delete exist file \"{}\" failed!!!",path,new Exception("Delete exist file \""+path+"\" failed!!!"));
                    }
                }
                //创建文件
                if(!localFile.exists()){
                    //如果文件不存在，则创建新的文件
                    boolean newFile = localFile.createNewFile();
                    if (!newFile){
                        System.out.println("--error");
                    }
                    //log.info("Create file successfully,the file is {}",path);
                }

                //创建文件成功后，写入内容到文件里
                fos = new FileOutputStream(localFile);
                in = file.getInputStream();
                byte[] bytes = new byte[1024];

                int len = -1;

                while((len = in.read(bytes)) != -1) {
                    fos.write(bytes, 0, len);
                }

                fos.flush();
                //log.info("Reading uploaded file and buffering to local successfully!");
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                try {
                    if(fos != null){
                        fos.close();
                    }
                    if(in != null){
                        in.close();
                    }
                } catch (IOException e) {
                    System.out.println("error");
                    //log.error("InputStream or OutputStream close error : {}", e);
                    return false;
                }
            }
            return true;
        }else {
            return false;
        }
    }
}
