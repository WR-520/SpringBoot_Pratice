package com.ln.springdemo.tools;

import cn.hutool.core.date.DateUtil;
import com.ln.springdemo.bean.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import sun.security.rsa.RSAUtil;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataCache {
    private Map<Long,Object> dataMap = new HashMap<>();
//    初始化数据
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
     *   更新值到数据据库中 并且缓存更新缓存中存储的值
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
}
