package com.ln.springdemo.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ln.springdemo.bean.Img;
import com.ln.springdemo.service.Img.ImgService;
import com.ln.springdemo.tools.ResultVo;
import com.ln.springdemo.tools.ResultVoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class ImgController {
   @Value("${saveImage.path}")
   private String path;
    @ResponseBody
    @PostMapping("/uploadFilesImg")
   public ResultVo uploadFilesImg(@RequestParam(value = "file",required = false) MultipartFile file, HttpServletRequest request) throws IOException {
    if(ObjectUtil.isNotNull(file) && file.getSize() > 0 ){
//        上传文件的原文件名 //hello.jpg //clo.jpeg
        String oldFileName =  file.getOriginalFilename();
//      获得原文件的后缀名
        String suffix = oldFileName.substring(oldFileName.lastIndexOf("."));
//       重命名操作
        String newFileName = IdUtil.simpleUUID() + suffix;
//       指定文件存储位置
//       File.separator 的作用相当于 ' \  '
        String dirUploadImg = path + File.separator + "uploadImg";
        String imgFilePath = dirUploadImg + File.separator + newFileName;
        System.out.println(imgFilePath);
        File filePath = new File(imgFilePath);
//        判断存储图片的目录是否存在 不存在构建
        if(!filePath.exists()){
            filePath.mkdirs();//构建uploadImg目录
        }
        file.transferTo(filePath);
        return ResultVoUtils.success("文件上传成功","图片上传操作",newFileName);
    }else{
        return ResultVoUtils.success("文件上传成功","文件上传操作");
    }
  }
  @Autowired
  private ImgService imgService;

//
  @ResponseBody
  @PostMapping("/saveImg")
   public ResultVo saveImg(@RequestBody Img img){
        if(ObjectUtil.isNotNull(img)){
           boolean resBool = imgService.save(img);
           if(resBool){
               return ResultVoUtils.success("保存成功","保存图片信息操作");
           }else{
               return ResultVoUtils.error("error","保存图片信息操作");
           }

        }
        else{
            return ResultVoUtils.error("保存失败","保存图片信息操作");

        }
   }
   @ResponseBody
   @PostMapping("/showImgLimits")
  public ResultVo showImgLimits(String page,@RequestParam(defaultValue = "10", name = "limit")String limit){
     Map<String,Object> objectMap =  imgService.page(Integer.parseInt(page),Integer.parseInt(limit));
     Long count = (Long)objectMap.get("TotalElements");//总记录数
     List<Img> imgList = (List<Img>) objectMap.get("pageContent");
     return ResultVoUtils.success("图片分页查询",imgList,"图片流加载显示",count);
  }
}
