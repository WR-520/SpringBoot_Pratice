package com.ln.springdemo.Repository;

import com.ln.springdemo.bean.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {

}
