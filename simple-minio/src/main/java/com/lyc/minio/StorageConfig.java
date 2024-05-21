package com.lyc.minio;

import lombok.Data;

/**
 * @author: liuyucai
 * @Created: 2023/3/25 15:38
 * @Description:
 */
@Data
public class StorageConfig {

    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucketName;
    private String proxyEndpoint;
}
