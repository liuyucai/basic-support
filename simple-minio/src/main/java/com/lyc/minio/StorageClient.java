package com.lyc.minio;

import com.lyc.minio.utils.StringUtils;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * @author: liuyucai
 * @Created: 2023/3/25 15:33
 * @Description:
 */
@Data
public class StorageClient {

    private StorageConfig storageConfig;

    private MinioClient minioClient;

    public void init(StorageConfig storageConfig){

        try{
            this.storageConfig = storageConfig;
            // 使用MinIO服务的URL，端口，Access key和Secret key创建一个MinioClient对象
            this.minioClient =
                    MinioClient.builder()
                            .endpoint(storageConfig.getEndpoint())
                            .credentials(storageConfig.getAccessKey(), storageConfig.getSecretKey())
                            .build();
            // 检查存储桶是否已经存在
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(storageConfig.getBucketName()).build());
            if (!isExist) {
                // Make a new bucket called 'asiatrip'.
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(storageConfig.getBucketName()).build());
            } else {
                System.out.println("Bucket 'asiatrip' already exists.");
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (InvalidKeyException e){
            e.printStackTrace();
        }catch (InvalidResponseException e){
            e.printStackTrace();
        }catch (InsufficientDataException e){
            e.printStackTrace();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }catch (ServerException e){
            e.printStackTrace();
        }catch (InternalException e){
            e.printStackTrace();
        }catch (XmlParserException e){
            e.printStackTrace();
        }catch (ErrorResponseException e){
            e.printStackTrace();
        }
    }


    public String upload(PutObject putObject){

        try {
            String folder = "";
            if (StringUtils.isNotEmpty(putObject.getPath())) {
                folder = putObject.getPath();
                if (!folder.substring(folder.length() - 1).equals("/")) {
                    folder = folder + "/";
                }
            }
            // 生成随机图片名
            String uuid = UUID.randomUUID().toString().replace("-", "");

            String fileUrlName = folder+uuid + putObject.getFileName().substring(putObject.getFileName().lastIndexOf("."));

            minioClient.putObject(
                    PutObjectArgs.builder().bucket(storageConfig.getBucketName()).object(fileUrlName).stream(
                            putObject.getInputStream(), -1, 104857600)
                            .contentType(putObject.getContentType())
                            .build());
            return fileUrlName;
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
            System.out.println("HTTP trace: " + e.httpTrace());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            System.out.println("Error occurred: " + e);
            e.printStackTrace();
        }
        return null;
    }


}
