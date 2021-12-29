package com.ln.springdemo.Repository;

import com.ln.springdemo.bean.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//分页排序操作
@Repository
public interface BookPageSortRepository extends JpaRepository<Book, Integer> {

}
