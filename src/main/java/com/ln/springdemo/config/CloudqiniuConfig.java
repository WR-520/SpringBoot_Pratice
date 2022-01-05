package com.ln.springdemo.config;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.security.Key;

@Component
@ConfigurationProperties(prefix = "oss.qiniu") //通过Springboot中的ConfigurationProperties注解给属性赋值
@Data
public class CloudqiniuConfig {
    //域名
    private String path;

    //公钥
    private String accessKey;

    //密钥
    private String secretKey;

    //空间名
    private String bucket;

    public String uploadImgQiNiu(InputStream inputStream, String fileName) throws QiniuException {
        //构造一个指定区域的对象类配置
        Configuration configuration = new Configuration(Region.huanan());
        UploadManager uploadManager = new UploadManager(configuration);
        //构造凭证对象
        Auth auth = Auth.create(accessKey,secretKey);
        String upToken = auth.uploadToken(bucket);
//        String fileUrl = path+File.separator+fileName;
//        File file = new File(fileUrl); //文件流方式上传
//        Response response = uploadManager.put(fileUrl,fileName,upToken);
        Response response = uploadManager.put(inputStream,fileName,upToken,null,null);
        //解析回显信息
        DefaultPutRet defaultPutRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        //外网地址，可以通过这个地址访问 上传的图片
        String returnImgUrl = path+ File.separator+defaultPutRet.key;
        System.out.println("returnImgUrl = " + returnImgUrl);
        return returnImgUrl;
    }

    public void deleteImgQiNiu(String fileName) throws QiniuException {

        Configuration configuration = new Configuration(Region.huanan());
        Auth auth = Auth.create(accessKey,secretKey);
        BucketManager bucketManager = new BucketManager(auth,configuration);
        bucketManager.delete(bucket, fileName);
    }
}
