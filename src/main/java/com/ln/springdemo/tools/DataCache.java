package com.ln.springdemo.tools;
import cn.hutool.core.date.DateUtil;
import com.ln.springdemo.bean.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Component
@CacheConfig(cacheNames = "cacheDemo") //统一对需要注解的value进行管理
public class DataCache {
      @Resource
      private RedisService redisService;

    private Map<Long,Object> dataMap = new HashMap<>();
//    初始化数据
//    1.@PostConstruct该注解被用来修饰一个非静态的void（）方法。
//    被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，
//    并且只会被服务器执行一次。PostConstruct在构造函数之后执行，
//    init（）方法之前执行。
//    2.通常我们会是在Spring框架中使用到@PostConstruct注解 该注解的方法在整个Bean初始化中的执行顺序：
//    Constructor(构造方法) -> @Autowired(依赖注入) -> @PostConstruct(注释的方法)
    @PostConstruct
    public void init(){
//        缓存存储对象必须要实现序列化，也就是实体类
      dataMap.put(1L,new User(1,"老唐","123456","18665656565","2021-01-06"));
      dataMap.put(2L,"深圳");
      dataMap.put(3L,"上海");
    }

    /**
     * 查询
     *  如果数据没有缓存，那么从dataMap里面获取，并缓存，并且将缓存的数据存储
     * @Cacheable属性
     *   1. cacheNames/value: 指定缓存组件的名字
     *   2. key缓存数据使用的key，
     *   3. keyGenerator: 作用与key一样 二选一
     *   4. cacheManager: 缓存管理器
     *   5. cacheResolver: 缓存解析器
     *   6. condition: 指定缓存条件 Eg: condition = "#id > 3 " 传入的参数id > 3才缓存数据
     *   7. unless 否定缓存， 当unless = true时不缓存
     *   8. sync: 是否使用异步模式
     */
    @Cacheable(value = "cacheDemo",key = "#id +'dataMap'" ) // ===> cacheDemo::1dataMap ===>广州 set key value get key
    public Object query(Long id){
        System.out.println("当前时间" + DateUtil.now() +"查询id的值为"+id);
        return  dataMap.get(id);
    }

    /**
     * 更新
     *   更新值到数据库中 并且缓存更新缓存中存储的值
     *
     * @CachePut 主要针对方法配置，能够根据方法的请求参数对其结果进行缓存
     * 注意点：
     *  1. 更新操作注解中的key和value必须要和更新的缓存相同，也就是说要与@Cacheable中的value和key相同
     *  2. CachePut必须与@Cacheable注解配合使用才有意义
     *
     */
    @CachePut( value = "cacheDemo",key="#id + 'dataMap'")
    public String put(Long id, String value){
        System.out.println("当前时间" + DateUtil.now() + "更新id的值为" + id);
        dataMap.put(id,value);
        return value;
    }
    /**
     * 删除
     *   删除dataMap里面的数据
     *   并且删除缓存cacheDemo中的数据
     *
     * @CacheEvict 清除缓存 删除数据库数据
     *  1. key: 指定要清除缓存中的某条数据
     *  2. allEntries = true ：删除缓存中的所有数据
     *  3. beforeInvocation = false 在方法执行之后执行清除缓存
     *  beforeInvocation = true
     *     使用在方法之前执行的好处：
     *         如果方法出现异常，缓存依旧会被删除
     */

    @CacheEvict( value = "cacheDemo",allEntries = true,beforeInvocation = false)
    public void remove(Long id){
        System.out.println("当前时间" + DateUtil.now() + "更新id的值为" + id);
        dataMap.remove(id);
    }

    /**
     * redis计数器
     *   在实际开发过程中产生组给定的需求：有规律的订单号
     *   Eg:
     *    订单号： 20220108DD0000001 20220108DD0000002
     *    20220108DD0000003
     */
    public String getOrderCode(String type){
        SimpleDateFormat sdfm = new SimpleDateFormat("yyyyMMdd");

        String curDate = sdfm.format(System.currentTimeMillis());
//        将生成的订单数存储到redis中
        Long number = redisService.incr(type + curDate,1L);
        String orderCode = padRight(number.toString(),7,"0");
        return curDate + type +  orderCode;//20220108DD0000002
    }

    /**
     * 右补位，左对齐
     * @param oriStr 原字符串
     * @param len 目标字符串位数
     * @param ale 补位字符
     * @return
     */
    public String padRight(String oriStr,int len , String ale) {
        String str = "";
        int strLen = oriStr.length();
        if ( strLen < len ) {
            for (int x = 0; x < len - strLen; x++) {
                str += ale;
            }
        }
        str = str + oriStr;
        return str;
    }
}
