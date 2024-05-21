package com.lyc.support.controller;

import com.lyc.common.vo.ResponseVO;
import com.lyc.minio.PutObject;
import com.lyc.minio.StorageClient;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.InputStream;
import java.util.Map;

/**
 * @author: liuyucai
 * @Created: 2023/03/23 9:14
 * @Description: 文件上传
 */
@Log4j2
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private StorageClient storageClient;

    @ResponseBody
    @RequestMapping(value = "/upload", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseVO<String> uploadFile(@RequestPart("file") MultipartFile file) {

        ResponseVO<String> responseVO = null;
        try {
            if (file != null) {

                InputStream inputStream = file.getInputStream();

                PutObject putObject = new PutObject();
                putObject.setContentType(file.getContentType());
                putObject.setFileName(file.getOriginalFilename());
                putObject.setInputStream(inputStream);
                putObject.setPath("com/lyc");

                String url = storageClient.upload(putObject);

                responseVO = ResponseVO.success(url);
            }
        } catch (Exception e) {
            log.error("上传文件异常");
            responseVO = ResponseVO.fail();
        }
        return responseVO;
    }
}
