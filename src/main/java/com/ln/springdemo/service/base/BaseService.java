package com.ln.springdemo.service.base;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public interface BaseService<T, ID> {
    //分页查询
    Map<String, Object> page(int pageNum, int pageSize, T entity);

    Map<String, Object> page(int pageNum, int pageSize);

    //查询所有
    List<T> selectAll();

    //根据主键查询
    Optional<T> selectById(ID id);

    //保存
    T save(T entity);

    //批量更新
    List<T> saveBatch(List<T> entity);

    //删除对象
    void deleteById(ID id);

    //批量删除
    void deleteBatch(ID[] ids);

    //更新
    T update(T entity, ID id);
}