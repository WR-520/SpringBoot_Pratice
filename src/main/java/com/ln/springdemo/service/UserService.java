package com.ln.springdemo.service;

import com.ln.springdemo.Repository.UserRepository;
import com.ln.springdemo.bean.User;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

public interface UserService  {
    /**
     *  1、方法命名方式进行绑定查询
     *  2、基于@query注解的方式进行查询
     */
   public User login(String userName, String password);
   public boolean save(User user);
//   public User findById(Integer id);
   public int updatePasswordById(int id,String password);
    /**
     * @Caching是@Cacheable、@CachePut、@CacheEvict注解的组合
     *  其拥有三个属性：Cacheable、put、evinct 分别用于指定@CaCacheable、@CachePut @CacheEvict
     *  作用：
     *      1. 当使用指定名字查询数据库后，数据保存到缓存
     *      2. 只要是对应的属性id、username、age就会直接查询缓存 而不是查询数据库
     */
    @Caching(
            /**
             * methodName =====> key = '#root.methodName' ===>当前被调用的方法
             * target ===> key="#root.target" ===> 当前被调用的目标对象
             * targetClass ===> key = "root.targetClass" ===> 当前被调用的目标对象类
             * "#a0"、"#p0" 方法参数的名字，0表示参数的索引
             *  key = "#id" key="#p0"
             *  result ===> 方法执行后的返回值 ===> '#result.id'
             *
             *  1. 缓存击穿  ====> 大量并发同时查询一个正好过期的数据
             *  2. 缓存雪崩  ====> 大量key同时过期
             *  3. 缓存穿透  ====> 查询一个null的数据
             **/
            //key可以使用Spring表达式语言来指定
            cacheable = {@Cacheable(value = "cacheDemo" ,key = "#result + 'dataMap'")},
            put ={@CachePut(value = "cacheDemo",key = "#result.age") }
            )
    public User findById(Integer id);

}

