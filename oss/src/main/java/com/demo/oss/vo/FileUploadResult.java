package com.demo.oss.vo;

import lombok.Data;

/**
 * @author shkstart
 * @create 2020-07-15 15:40
 */
@Data
public class FileUploadResult {
    // 文件名
    private String name;
    // 状态有：uploading done error removed
    private String status;
    // 服务端响应内容，如：'{"status": "success"}'
    private String response;
}