package com.ln.springdemo.controller;

import com.ln.springdemo.bean.DogProperties;
import com.ln.springdemo.bean.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


/**
 * @RestController + @Controller+@ResponseBody
 * 在一个Controller中如果使用RestController注解，则controller中的方法无法返回return中的内容
 * 如果需要返回到指定的页面，则需要使用@Controller
 * 如果需要返回JSON、xml格式的内容到页面上，则需要在对应的方法上加上@ResponseBody
 * @ResponseBody注解作用：将Controller中方法执行的结果转换为JSON格式的字符串
 */
//@RestController
@Controller
@RequestMapping("/book")
public class WebControllerTest {
    //http:localhost:9101/book/hello

    @Autowired
    DogProperties dogproperties;


    /**
     * @RequestMapping:用于处理请求地址，可以作用于类和方法上 1、value：定义request请求的映射地址
     * 2、method：定义请求的方式（GET、POST、HEAD、OPTIONS、PUT、PATCH、DELETE、TRACE）
     * 3、params：定义request请求中必须包含的参数值[注意点]
     */
    //@GetMapping("/hello")Resultful风格方式的请求处理
    @RequestMapping(value = "/hello", method = RequestMethod.GET, params = "name")
    /**
     * @RequestParam:用于获取传入的参数值
     *  1、value：参数的名称
     *  2、required：定义该传入参数是否必须，默认为true
     *  3、当URL中传递的参数名与获取参数值的属性名不一致时，则u需要通过value属性来进行映射匹配
     */

    /**
     * @Controller
     * @RequestMapping
     * @RequestParam
     * @ResponseBody：将controller中的方法返回的对象，通过适当的转换为指定的数据格式（JSON、XML）
     * @RequestBody：将请求的数据绑定到指定的数据对象上
     */

    public String hello(@RequestParam(value = "name", required = true) String bookName) {
        return "SpringBoot整合SpringMVC的操作！" + bookName;
    }

    /**
     * @Controller层中的方法处理完后要返回视图，SpringMVC支持如下的返回方式： 1、ModelAndView()
     * 2、model()
     * 3、String()
     * 4、void()
     * 5、Map
     * 6、ModelMap
     * 7、View
     */
    @Autowired
    Person person;

    //    @Resource
//    BookTest bookTest;
    @RequestMapping(value = "/showModelAndView", method = RequestMethod.GET)
    public ModelAndView showModelAndView() {
        //构造ModelAndView对象
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg", "springMVC返回值之ModelAndView类型");
        mv.addObject("person", person);
//        mv.addObject("book", bookTest);
        //跳转到指定的视图
        mv.setViewName("showTest");
        return mv;
    }

    @RequestMapping(value = "/showModel")
    public String showModel(Model model) {
        model.addAttribute("resultMsg", "返回HTML页面操作");
        model.addAttribute("dog", dogproperties);
        return "/showModel/modelTest.html";
    }


}
