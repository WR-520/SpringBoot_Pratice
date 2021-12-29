package com.ln.springdemo.service.book.impl;

import cn.hutool.core.util.StrUtil;
import com.ln.springdemo.Repository.BookRepository;
import com.ln.springdemo.bean.Book;
import com.ln.springdemo.service.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;


@Service
public class BookServiceImpl implements BookService {
    //    @Autowired
//    BookPageSortRepository bookPageSortRepository;
    @Autowired
    private BookRepository bookRepository;

    /**
     * toPredicate：构造查询条件的主要方法
     * Root:表示可查询和操作实体对象的根，如果将实体对象比喻为表名，root则为表的字段
     *
     * CriteriaQuery: 查询操作对象，它包含查询操作的各个组件部分:select、from、where、group by、order by
     * 提供Root查询的方法
     *
     * CriteriaBuilder: 构造器对象，其实相当于条件或条件组合
     *
     */
    public Map<String, Object> page(int pageNum, int pageSize, String entity) {
//Specification:参数接口
        Specification sp = new Specification<Book>(){
            /**
             *toPredicate:构造查询条件的主要方法
             * Root:表示可查询和可操作实体对象的根，如果将实体对象比喻为表名，root则为表的字段
             *
             * CriteriaQuery:查询操作对象，它包含查询操作的各个组件部分：select、from、where、group by、order by
             * 提供Root查询的方法
             *
             * CriteriaBuilder：构造器对象，其实相当于条件或条件组合
             */
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = null;
//hasBlank:hutool工具包中的封装的方法，用于判断字符串是否为空，当字符串不为空返回false
                if (!StrUtil.hasBlank(entity)){
                    predicate = criteriaBuilder.like(root.get("name").as(String.class), "%"+entity+"%");
                }
                return predicate;
            }
        };
        Map<String,Object> map = new HashMap<>();
//分页，排序，按id排序
        PageRequest pageRequest = PageRequest.of(pageNum-1,pageSize, Sort.by(Sort.Direction.DESC,"id"));
        Page<Book> page = bookRepository.findAll(sp,pageRequest);
        map.put("TotalElements",page.getTotalElements());
        map.put("pageContent",page.getContent());
        return map;
    }

//    @Override
//    public Map<String, Object> page(int pageNum, int pageSize, String entity) {
//        return null;
//    }

    @Override
    public Map<String, Object> page(int pageNum, int pageSize) {
//        分页，排序，排列规则:按id降序
        Map<String, Object> map = new HashMap<>();
//     JPA分页之PageRequest.of代替过时的PageRequest方法
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "id"));
//      Example<实体> example = Example.of(实体)
        Page<Book> page = bookRepository.findAll(pageRequest);
        map.put("TotalElements", page.getTotalElements());
        map.put("pageContent", page.getContent());
        return map;
    }

    @Override
    public List<Book> selectAll() {
        return null;
    }

    @Override
    public Optional<Book> selectById(Object id) {
        return Optional.empty();
    }

    @Override
    public Book save(Book entity) {
        return bookRepository.save(entity);
    }

    @Override
    public List<Book> saveBatch(List<Book> entity) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void deleteBatch(Object[] ids) {
        List listIds = new ArrayList<>();
        for (Object id : ids) {
//listIds.add(id);
            bookRepository.deleteById(Integer.parseInt(String.valueOf(id)));
        }
    }

    @Override
    public Book update(Book entity, Object id) {
        return null;
    }
}