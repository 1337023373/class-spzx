package com.atguigu.spzx.manager.test;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.file.FileNameUtil;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.MinioException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class MinioTest {
    public static void main(String[] args)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            //创建MinioClient对象
            MinioClient minioClient =
                    MinioClient.builder()
                            //设置服务器的地址
                            .endpoint("http://localhost:9000")
                            .credentials("minioadmin", "minioadmin")
                            .build();

            //判断某个bucket是否存在
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket("spzx").build());
            if (!found) {
                //如果bucket不存在就创建出来
                minioClient.makeBucket(MakeBucketArgs.builder().bucket("spzx").build());
            } else {
                System.out.println("Bucket 'spzx' already exists.");
            }

            //上传文件到minio服务器
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            //设置buket的名字
                            .bucket("spzx")
                            //上传到服务器之后的名字
                            .object("12.5.jpg")
                            //指定要上传的文件的路径
                            .filename("C:/Users/Mr.亨亨/Desktop/12.5.jpg")
                            .build());
            System.out.println(
                    "'C:/Users/Mr.亨亨/Desktop/12.5.jpg' is successfully uploaded as "
                            + "object '12.5.jpg' to bucket 'spzx'.");
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
            System.out.println("HTTP trace: " + e.httpTrace());
        }
    }
}
