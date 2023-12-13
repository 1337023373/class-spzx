package com.atguigu.spzx.manager.service.impl;

import cn.hutool.core.date.DateUtil;
import com.atguigu.spzx.manager.properties.MyMinioProperties;
import com.atguigu.spzx.manager.service.FileUploadService;
import io.minio.*;
import io.minio.errors.MinioException;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

@Service
@Transactional
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private MyMinioProperties myMinioProperties;

    @Override
    public String upload(MultipartFile file) {
        try {
            //创建MinioClient对象
            MinioClient minioClient =
                    MinioClient.builder()
                            //设置服务器的地址
                            .endpoint(myMinioProperties.getEndpointUrl())
                            .credentials(myMinioProperties.getAccessKey(), myMinioProperties.getSecretKey())
                            .build();

            //判断某个bucket是否存在
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(myMinioProperties.getBucketName()).build());
            if (!found) {
                //如果bucket不存在就创建出来
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(myMinioProperties.getBucketName()).build());
            } else {
                System.out.println("Bucket "+myMinioProperties.getBucketName()+" already exists.");
            }
            //获取输入流
            InputStream is = file.getInputStream();
            //获取上传文件的文件名
            String filename = file.getOriginalFilename();
            //获取文件的后缀
            String suffix = FileNameUtils.getExtension(filename);
            //通过UUID随机生成一个字符串作为上传的文件的名字
            String prefix = UUID.randomUUID().toString().replaceAll("-", "");
            //对当前日期进行格式化
            String dateFormat = DateUtil.format(new Date(), "yyyy/MM/dd");
            //设置文件的名字
            String newFilename = dateFormat+"/"+prefix+"."+suffix;
            //创建PutObjectArgs对象
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    //设置buket的名字
                    .bucket(myMinioProperties.getBucketName())
                    //设置上传文件的输入流
                    .stream(is, file.getSize(), -1)
                    //上传到服务器之后的名字
                    .object(newFilename)
                    .build();
            //上传文件
            minioClient.putObject(putObjectArgs);
            //设置返回的访问文件的地址
            String fileUrl = myMinioProperties.getEndpointUrl()+"/"+myMinioProperties.getBucketName()+"/"+newFilename;
            return fileUrl;
        } catch (Exception e) {
            System.out.println("Error occurred: " + e);
        }
        return null;
    }
}
