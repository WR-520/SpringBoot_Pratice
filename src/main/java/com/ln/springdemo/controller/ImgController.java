package com.ln.springdemo.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.ln.springdemo.bean.Img;
import com.ln.springdemo.config.CloudqiniuConfig;
import com.ln.springdemo.service.Img.ImgService;
import com.ln.springdemo.tools.ResultVo;
import com.ln.springdemo.tools.ResultVoUtils;
import com.qiniu.common.QiniuException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Controller
public class ImgController {

    @Value("${saveImage.path}")
    private String path;

    @Autowired
    private ImgService imgService;

    @Resource
    private CloudqiniuConfig cloudqiniuConfig;

    @ResponseBody
    @PostMapping("/uploadFilesImg")
    public ResultVo uploadFilesImg(@RequestParam(value = "file", required = false) MultipartFile file,
                                   HttpServletRequest request) throws IOException {
        if (ObjectUtil.isNotNull(file) && file.getSize() > 0) {
            //上传文件的原文件名  /hello.woller.jpg  //clo.jpeg
            String oldFileName = file.getOriginalFilename();
            //获得原文件的后缀名
            String suffix = oldFileName.substring(oldFileName.lastIndexOf("."));
            //重命名操作
            String newFileName = IdUtil.simpleUUID() + suffix;
            //指定文件存储位置
            //path/uploadImg
            String dirUploadImg = path + File.separator + "uploadImg";
            String imgFilePath = dirUploadImg + File.separator + newFileName;
            File filePath = new File(imgFilePath);
            //判断存储图片的目录是否存在，不存在构建
            if (!filePath.exists()) {
                filePath.mkdirs();//构建uploadImg目录
            }
            file.transferTo(filePath);
            return ResultVoUtils.success("文件上传成功", "图片上传操作",newFileName);
        } else {
            return ResultVoUtils.error("文件上传失败", "图片上传操作");
        }
    }

    @ResponseBody
    @PostMapping("/uploadFilesImgCloud")
    public ResultVo uploadFilesImgCloud(@RequestParam(value = "file", required = false) MultipartFile file,
                                   HttpServletRequest request) throws IOException {
        if (ObjectUtil.isNotNull(file) && file.getSize() > 0) {
            //上传文件的原文件名  /hello.woller.jpg  //clo.jpeg
            String oldFileName = file.getOriginalFilename();
            //获得原文件的后缀名
            String suffix = oldFileName.substring(oldFileName.lastIndexOf("."));
            //重命名操作
            String newFileName = IdUtil.simpleUUID() + suffix;
            //构造流对象
            InputStream inputStream = file.getInputStream();
            String cloudUploadFile = cloudqiniuConfig.uploadImgQiNiu(inputStream,newFileName);
            return ResultVoUtils.success("文件上传成功", "上传图片到七牛云服务",cloudUploadFile);
        } else {
            return ResultVoUtils.error("文件上传失败", "上传图片到七牛云服务");
        }
    }


    @ResponseBody
    @PostMapping("/saveImg")
    public ResultVo saveImg(@RequestBody Img img){
        if (ObjectUtil.isNotNull(img)){
            boolean resBool = imgService.save(img);
            if (resBool){
                return ResultVoUtils.success("保存成功","保存图片信息操作");
            }else {
                return ResultVoUtils.error("error","保存图片信息操作");

            }
        }else {
            return ResultVoUtils.error("保存失败","保存图片信息操作");
        }
    }

    @ResponseBody
    @PostMapping("/showImgLimits")
    public ResultVo showImgLimits(String page,@RequestParam(defaultValue = "10",name="limit") String limit){
        Map<String,Object> objectMap = imgService.page(Integer.parseInt(page),Integer.parseInt(limit));

        Long count =(Long)objectMap.get("TotalElements");//总记录数
        List<Img> imgList = (List<Img>) objectMap.get("pageContent");
        return ResultVoUtils.success("图片分页查询",imgList,"图片流加载显示",count);
    }

    @ResponseBody
    @PostMapping("/deleteById")
    public ResultVo deleteById(String id) throws QiniuException {
        //hasEmpty():非空验证，为空返回true
        if (!StrUtil.hasEmpty(id)){
            int ids = Integer.parseInt(id);
            Img img = imgService.findById(ids);
            //File file = new File(path + File.separator + "uploadImg"+File.separator+img.getImgPath());
//            File file = new File(img.getImgPath());
//                if (file.exists()){
                //file.delete();
            String delImgName = img.getImgPath().substring(img.getImgPath().lastIndexOf("\\")+1);
            System.out.println("========>"+delImgName);
            cloudqiniuConfig.deleteImgQiNiu(delImgName);
            //删除数据库表记录的同时要删除存储在uploadImg中的对应的图片
            imgService.deleById(String.valueOf(ids));
//            }
            return ResultVoUtils.success("删除成功","执行删除图片操作");
        }else {
            return ResultVoUtils.error("删除失败","执行删除图片操作");
        }
    }

    @ResponseBody
    @PostMapping("deleteAllByIds")
    public ResultVo deleteAllByIds(@RequestParam(value = "delIds[]") String [] delIds) throws QiniuException {
        if (!ArrayUtil.hasEmpty(delIds)){
            //String[] delIds = ids.split("ids;");
            for (String id : delIds){
                int delId = Integer.parseInt(id);
                Img img = imgService.findById(delId);
//                File file = new File(path + File.separator + "uploadImg"+File.separator+img.getImgPath());
//                if (file.exists()){
//                    file.delete();
//                    //删除数据库表记录的同时要删除存储在uploadImg中的对应的图片
//                    imgService.deleteById(delId);
//                }
                String delImgName = img.getImgPath().substring(img.getImgPath().lastIndexOf("\\")+1);
                System.out.println("========>"+delImgName);
                cloudqiniuConfig.deleteImgQiNiu(delImgName);
                //删除数据库表记录的同时要删除存储在uploadImg中的对应的图片
                imgService.deleById(String.valueOf( delId ));
            }
            return ResultVoUtils.success("删除成功","执行删除图片操作");
        }else {
             return ResultVoUtils.error("删除失败","执行删除图片操作");
        }
    }
}
