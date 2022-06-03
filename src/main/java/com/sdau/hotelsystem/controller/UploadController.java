package com.sdau.hotelsystem.controller;

import com.sdau.hotelsystem.domain.User;
import com.sdau.hotelsystem.service.UserService;
import com.sdau.hotelsystem.util.UploadResult;
import com.sdau.hotelsystem.util.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RequestMapping("upload")
@RestController
public class UploadController {

    @Autowired
    private UserService service;

    @Autowired
    private HttpSession session;

    /**
     * 上传头像
     * @param multipartFile
     * @return
     */
    @RequestMapping("/image")
    public UploadResult uploadImage(@RequestParam(value = "file") MultipartFile multipartFile){

        User token = (User)session.getAttribute("currentUser");
        String imagePath = UploadUtils.upload(multipartFile);
        service.upload(token.getId(),imagePath);
        UploadResult result = new UploadResult();
        if (imagePath != null) {
            result.setCode(1);
            result.setMsg("上传成功");
            HashMap<String,String> map = new HashMap<>();
            map.put("src",imagePath);
            result.setData(map);
        }else {
            result.setCode(0);
            result.setMsg("上传失败");
        }
        return result;
    }

    /**
     * 上传新闻图片
     * @param multipartFile
     * @return
     */
    @RequestMapping("/pic")
    public UploadResult uploadPic(@RequestParam(value = "file") MultipartFile multipartFile){

        String imagePath = UploadUtils.upload(multipartFile);
        UploadResult result = new UploadResult();
        if (imagePath != null) {
            result.setCode(0);
            result.setMsg("上传成功");
            HashMap<String,String> map = new HashMap<>();
            map.put("src","../"+imagePath);
            result.setData(map);
        }else {
            result.setCode(1);
            result.setMsg("上传失败");
        }
        return result;
    }

    /**
     * 上传证件
     * @param multipartFile
     * @return
     */
    @RequestMapping("/card")
    public UploadResult uploadCard(@RequestParam(value = "file") MultipartFile multipartFile){

        String imagePath = UploadUtils.upload(multipartFile);
        UploadResult result = new UploadResult();
        if (imagePath != null) {
            result.setCode(0);
            result.setMsg("上传成功");
            result.setSrc(imagePath);
        }else {
            result.setCode(1);
            result.setMsg("上传失败");
        }
        return result;
    }
}
