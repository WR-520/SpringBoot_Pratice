package com.ln.springdemo.service.book;

import com.ln.springdemo.bean.Book;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BookService {
    //分页条件查询
    Map<String, Object> page(int pageNum, int pageSize, String entity);

    //分页查询
    Map<String, Object> page(int pageNum, int pageSize);


    //查询所有
    List<Book> selectAll();

    //根据主键查询
    Optional<Book> selectById(Object id);

    //保存
    Book save(Book entity);

    //批量更新
    List<Book> saveBatch(List<Book> entity);

    //删除对象
    void deleteById(Integer id);

    //批量删除
    void deleteBatch(Object[] ids);

    //更新
    Book update(Book entity, Object id);
}