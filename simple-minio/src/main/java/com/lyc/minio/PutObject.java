package com.lyc.minio;

import lombok.Data;

import java.io.InputStream;

/**
 * @author: liuyucai
 * @Created: 2023/3/25 15:59
 * @Description:
 */
@Data
public class PutObject {

    private InputStream inputStream;
    private String fileName;
    private String path;
    private String contentType;
    private long size;
}
