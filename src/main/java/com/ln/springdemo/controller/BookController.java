package com.ln.springdemo.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import com.ln.springdemo.service.book.BookService;
import com.ln.springdemo.tools.ResultVo;
import com.ln.springdemo.tools.ResultVoUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.ln.springdemo.bean.Book;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/book")
@Api("图书管理模块")
public class BookController {
    @Autowired
    private BookService bookService;

    //列表查询
    @GetMapping("/listBook")
    //http://localhost:9101/book/listBook
    //http://localhost:9101/book/listBook?page=1&limit=10
    @ResponseBody
    public ResultVo listBook(@RequestParam(defaultValue = "1", name = "page") String pageNum,
                             @RequestParam(defaultValue = "10", name = "limit") String pageSize
     ,String strName) {
        Map<String, Object> objectMap = bookService.page(Integer.parseInt(pageNum), Integer.parseInt(pageSize),strName);
        //总记录数
        Long count = (Long) objectMap.get("TotalElements");
        List<Book> bookList = (List<Book>) objectMap.get("pageContent");
        //return new ResultVo(0,"列表分页查询",bookList,"基于Layui 动态表格数据渲染",count);
        return ResultVoUtils.success("列表分页查询", bookList, "基于Layui 动态表格数据渲染", count);
    }

    @GetMapping("bookHtml")
    public String bookHtml() {
        return "bookpage/booklist";//ajax（异步请求）
    }

    @DeleteMapping("/deleteById")
    @ResponseBody
    public ResultVo deleteById(@RequestBody JSONObject jsonObject) {
        /**
         * @ResponseBody（后端响应操作）
         * 将后端以return返回的javabean类型数据转换为json格式数据
         *
         * @RequestBody(前端请求传递参数操作)
         * 将前端传递过来的JSON格式的数据转化为自定义的javabean类型
         *
         * 常见错误类型
         *  1、404：请求资源路径不对
         *  2、500：业务逻辑错误
         *  3、405：请求方式不匹配
         *
         */
        int id = Integer.parseInt(jsonObject.getStr("id"));
        bookService.deleteById(id);
        return ResultVoUtils.success("执行成功", "列表数据行删除操作");
    }

    @GetMapping("/deleteAllCheck")
    @ResponseBody
    @ApiOperation("根据id进行删除操作")
    public ResultVo deleteAllCheck(String ids) {
        String[] strIds = ids.split(";");
        Integer[] IntIds = new Integer[strIds.length];
        for (int i = 0; i < strIds.length; i++) {
            IntIds[i] = Integer.parseInt(strIds[i]);
        }
        bookService.deleteBatch(IntIds);
        return ResultVoUtils.success("删除成功", "列表数据行批量删除操作！！");
    }
  @GetMapping("/showInfo")
    public String showInfo(){
        return "/page/showInfo";
  }
  @GetMapping("/addOrUpdateInfo")
  public String addOrUpdateInfo(){

        return "/page/addOrUpdateInfo";
  }

  //  跳转到详情信息页面
  @PostMapping("/saveBean")
  @ResponseBody
  public ResultVo saveBean(@RequestBody Book book){
      if (ObjectUtil.isNotNull(book)){
          Book BookVo = bookService.save(book);
          if (ObjectUtil.isNotNull(BookVo)){
              return ResultVoUtils.success("执行成功","列表添加操作！！");
          }
          else{
              return ResultVoUtils.error("执行失败","列表添加操作！！");
          }
      }else{
          return ResultVoUtils.error("执行失败","列表添加操作！！");
      }
  }


}