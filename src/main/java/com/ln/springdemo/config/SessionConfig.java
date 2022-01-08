package com.ln.springdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Cookie-session
 * 在服务端寻找一个空间存储所有用户会话的状态信息，并给每个用户分配不同的sessionid
 * 再将这个sessionid推送给浏览器客户端存储在cookie中记录当前状态
 * 下次请求的时候只需要携带这个sessionid 服务端就可以去那个空间找该标志对应的用户
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 30*24*60*60)
public class SessionConfig {
   //如果设置session失效时间，则在yml文件中配置的失效时间就会过期
}
/**
 * 如何实现多个服务器直接的session共享 ？
 *   其实只需要按照上面的步骤在另外一个项目中再次配置一次
 */
