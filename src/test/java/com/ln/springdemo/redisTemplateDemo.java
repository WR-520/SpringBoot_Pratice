package com.ln.springdemo;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class redisTemplateDemo {
    //  SpringRedisTemplate是Spring Data Redis对jedis API的高度封装
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RedisTemplate<String,Object>  redisTemplate;
    /**
     * 两者区别：
     *   StringRedisTemplate使用的JdkSerializationRedisSerialiizer序列化
     *   其存储数据会先序列化成字节数组然后再存入redis中
     *
     *   StringRedisTemplate使用的是StringRedisSerializer
     *
     *   当redis中存储的是字符串数据或实现StringRedisTemplate
     *   复杂结构的对象类型数据，使用redisTemplate更好
     */
    public void stringRedisTemplateTest(){
        //给指定的键设置过期时间
        redisTemplate.expire("class1902",12, TimeUnit.SECONDS);
//          redisTemplate.delete(""); 删除指定的键
        //        redisTemplate.hasKey("");//判断键是否存在
        redisTemplate.getExpire("");
    }
}
