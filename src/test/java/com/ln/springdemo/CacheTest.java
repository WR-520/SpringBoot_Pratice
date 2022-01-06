package com.ln.springdemo;

import com.ln.springdemo.tools.DataCache;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class CacheTest {
    @Resource
    private DataCache dataCache;

    @Test //查询
    public void cacheableTest(){
       Object dataValue  = dataCache.query(1L);
        System.out.println(dataValue);
    }
   @Test
    public void CachePutTest(){
       String updateValue  =  dataCache.put(1L,"北京");
       System.out.println(updateValue);
   }

}
