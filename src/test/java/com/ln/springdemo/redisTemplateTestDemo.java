package com.ln.springdemo;

import com.ln.springdemo.bean.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class redisTemplateTestDemo {

    // StringRedisTemplate是Spring Data Redis相对于jedis API的高度封装
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 两者区别：
     *  RedisTemplate使用的JdkSerializationRedisSerializer序列化
     *  其存储数据会先序列化成字节数组然后再存入redis中
     *
     *  StringRedisTemplate使用的是StringRedisSerializer
     *
     *  当redis中存储的是字符串数据就实现StringRedisTemplate
     *  复杂结构的对象类型数据，使用redisTemplate
     */
    @Test
    public void stringRedisTemplateTest(){

        //使用StringRedisTemplate存储对象

        redisTemplate.opsForValue().set("user",new User(1,"老谭","123","18778889999","2022-1-6"));


        //给指定的键设置过期时间
//        stringRedisTemplate.expire("class1902",12, TimeUnit.SECONDS);
//        stringRedisTemplate.delete("class1902"); 删除指定的键
//        stringRedisTemplate.hasKey("class1902"); 判断键是否存在
//        stringRedisTemplate.getExpire("class1902"); //获取过期时间
//        stringRedisTemplate.getExpire("class1902",TimeUnit.SECONDS);//获取指定格式的过期时间
//        stringRedisTemplate.rename("class1902","newClass1902");//key，旧换新，如果修改的key不存在，则报错
//        stringRedisTemplate.renameIfAbsent("class1902","newClass1902");
//        stringRedisTemplate.type("newClass1902");//获取指定key的类型
//        stringRedisTemplate.move("newClass19",3);//将指定的key移动到指定库中
//        stringRedisTemplate.randomKey();//随机获取一个key
//        stringRedisTemplate.persist("newClass19");//持久化操作，将key设置为永不过期
//        stringRedisTemplate.opsForValue().set();

        //map结构一次性存储多个键值对key-value
//        Map<String,String> map = new HashMap<>();
//        map.put("k1","v1123");
//        map.put("k2","v1123");
//        map.put("k3","毒树");
//        stringRedisTemplate.opsForValue().multiSet(map);
//        stringRedisTemplate.opsForValue().size();//获取key值长度


    }


}
