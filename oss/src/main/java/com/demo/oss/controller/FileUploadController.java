package com.demo.oss.controller;

import com.aliyun.oss.model.OSSObjectSummary;
import com.demo.oss.service.FileUploadService;
import com.demo.oss.service.MultipartUploadService;
import com.demo.oss.vo.FileUploadResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author shkstart
 * @create 2020-07-15 16:01
 */
@RestController
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private MultipartUploadService multipartUploadService;

    @PostMapping("file/upload")
    public FileUploadResult upload(@RequestParam("file") MultipartFile uploadFile) throws Exception {
        return this.fileUploadService.upload(uploadFile);
    }

    @PostMapping("file/multipartUpload")
    public FileUploadResult multipartUpload(@RequestParam("file") MultipartFile uploadFile) throws Exception {
        return this.multipartUploadService.multipartUpload(uploadFile);
    }

    /**
     * @desc 根据文件名删除oss上的文件
     */
    @GetMapping("file/delete")
    public FileUploadResult delete(@RequestParam("fileName") String objectName) throws Exception {
        return this.fileUploadService.delete(objectName);
    }


    @GetMapping("file/list")
    public List<String> list() throws Exception {
        return this.fileUploadService.list();
    }

    /**
     * 根据文件名下载oss上的文件
     */
    @GetMapping("file/download")
    public void download(@RequestParam("fileName") String objectName, HttpServletResponse response) throws IOException {

        //通知浏览器以附件形式下载
        response.setHeader("Content-Disposition", "attachment;filename=" + objectName);
        this.fileUploadService.exportOssFile(response.getOutputStream(),objectName);
    }

    /**
     * 根据文件名下载oss上的文件
     */
    @GetMapping("file/multipartdDownload")
    public void multipartdDownload(@RequestParam("fileName") String objectName, HttpServletResponse response) throws Throwable {


        multipartUploadService.exportOssFile(response.getOutputStream(),objectName);
    }
}

