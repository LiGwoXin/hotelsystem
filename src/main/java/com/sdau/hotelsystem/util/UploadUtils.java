package com.sdau.hotelsystem.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class UploadUtils {

    //上传路径
//    private static final String uploadPathImg="/root/final/uploadedImg/";
    private static final String uploadPathImg="C:\\img\\";

//    访问路径
    private static final String SERVER_PATH="uploadview/";

    public static String upload(MultipartFile file) {
        String fileName = System.currentTimeMillis() + file.getOriginalFilename();

        String upload_file_dir=uploadPathImg;//注意这里需要添加目录信息
        String destFileName =  uploadPathImg +fileName;

        //4第一次运行的时候，这个文件所在的目录往往是不存在的，这里需要创建一下目录（创建到了webapp下uploaded文件夹下）
        File upload_file_dir_file = new File(upload_file_dir);
        if (!upload_file_dir_file.exists())
        {
            upload_file_dir_file.mkdirs();
        }

        //浏览器上传的文件复制到希望的位置
        File targetFile = new File(upload_file_dir_file, fileName);
        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return SERVER_PATH + fileName;
    }
}
