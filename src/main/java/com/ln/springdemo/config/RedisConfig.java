package com.ln.springdemo.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Arrays;

/**
 * @ClassName RedisConfig
 * @Description: TODO
 * @Author hefeng
 * @Date 4:32 PM 1/7/22
 * @Version V1.0
 **/
@Configuration //表示当前类属于配置类
@EnableCaching //表示支持缓存

/**
 * 配置Spring Cache的CacheManger需要继承CachingConfigurerSupport类，重写cacheManager方法
 * 在此之前，确保spring容器里面有redisTemplate，需要使用redisTemplate获取LettuceConnectionFactory
 */
public class RedisConfig extends CachingConfigurerSupport {


    //自定义ke生成策略
    //策略：包名+类名+方法名+参数
    @Bean
    public KeyGenerator keyGenerator() {
        return((target, method, params) -> {
            StringBuilder sb  = new StringBuilder();
            sb.append(target.getClass().getName()).append(".");
            sb.append(method.getName()).append(Arrays.toString(params));
            return sb.toString();
        }) ;
    }
    /**
     * 默认情况下redisTemplate模板使用的是jdk方式进行序列化操作，不是很方便，所有需要重写序列化的实现

     *  为什么需要序列化？
     *     你要记住一句话，在JAVA中，一切皆对象，而将对象的状态信息转为存储或传输的形式需要序列化。

     * Spring Boot 在 RedisAutoConfiguration 中默认配置了 RedisTemplate<Object, Object>、
     * StringRedisTemplate两个模板类，然而RedisTemplate<Object, Object>并未指定key、value的序列化器。
     * Jackson2JsonRedisSerializer的序列化器需要引入Jackson的依赖
     *
     * RedisSerializer redis序列化的接口类
     * OxmSerializer xml到object的序列化/反序列化
     * StringRedisSerializer string字符串的序列化/反序列化
     * JacksonJsonRedisSerializer json到object的序列化/反序列化
     * Jackson2JsonRedisSerializer json到object的序列化/反序列化
     * JdkSerializationRedisSerializer java对象的序列化/反序列化
     *
     * RedisTemplate默认使用的是JdkSerializationRedisSerializer
     *
     * JdkSerializationRedisSerializer：JDK自带的序列化方式、存储的字符串内容在序列化的情况下偏长，会占用过多的内存
     * OxmSerializer：序列化的时间相对较长
     * Jackson2JsonRedisSerializer：json数据格式、序列化时间和序列化之后内容的长度都要优于前两种
     */
    //配置序列化器
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        // 配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(connectionFactory);

        // 设置序列化
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer =
                new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();
        //作用是序列化时将对象全类名一起保存下来
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance ,
                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        RedisSerializer<String> stringSerializer = new StringRedisSerializer();

        redisTemplate.setKeySerializer(stringSerializer);// key序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);// value序列化
        redisTemplate.setHashKeySerializer(stringSerializer);// Hash key序列化
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);// Hash value序列化
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    //配置 cacheManager 代替默认的cacheManager （缓存管理器）
    //JdkSerializationRedisSerializer：JDK自带的序列化方式、存储的字符串内容在序列化的情况下偏长，会占用过多的内存


    //配置 cacheManager 代替默认的cacheManager （缓存管理器）

    /**
     *CacheManager是对Cache进行管理、创建、获取、销毁等操作的，在创建Cache时，需要对其进行序列化。
     * 在重写cacheManager时，通过RedisCacheConfiguration 对缓存数据的key、value进行序列化操作
     * key对应String格式
     * value对应JSON格式，其目的也是为了解决缓存中中文乱码问题
     */
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        //spring cache注解序列化配置,分别创建String和JSON格式序列化对象，对缓存数据key和value进行转换
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                //解决中文乱码问题
                //key序列化方式
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getKeySerializer()))
                //配置缓存管理器为json序列化器 //value序列化方式
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getValueSerializer()))
                .disableCachingNullValues()  //不缓存null值
                .entryTtl(Duration.ofMinutes(60));  //默认缓存过期时间

        //使用自定义的配置构建缓存管理器
        RedisCacheManager redisCacheManager = RedisCacheManager.builder(redisTemplate.getConnectionFactory())
                .cacheDefaults(redisCacheConfiguration)
                .build();
        return redisCacheManager;
    }
}

