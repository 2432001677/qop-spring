package cn.edu.zucc.utils;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Bruce
 * @since 04-28-2021
 **/
@Component
@RefreshScope
public class MinioUtils {
    @Value("${minio.bucketName}")
    private String bucketName;
    @Resource
    private MinioClient minioClient;

    @SneakyThrows
    public boolean isBucketExists() {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }

    @SneakyThrows
    public void putObject(String objectName, String filePath) {
        if (!isBucketExists()) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
        minioClient.uploadObject(
                UploadObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .filename(filePath + bucketName)
                        .build()
        );
    }
}
