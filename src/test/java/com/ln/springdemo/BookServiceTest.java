package com.ln.springdemo;

import com.ln.springdemo.bean.Book;
import com.ln.springdemo.service.book.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class BookServiceTest {
    @Autowired
    BookService bookService;

    @Test
    public void listBookTest(){
       List<Book> bookList = bookService.selectAll();
       for (Book book:bookList){
           System.out.println(book);
       }
    }
//    分页查询
    @Test
    public void pageTest(){
        Book book  = new Book();
      Map<String,Object> objectMap = bookService.page(1,8);
      List<Book> bookTestList = ( List<Book> )objectMap.get("pageContent");
        for (Book book1 : bookTestList) {
            System.out.println(book.toString());
        }
        System.out.println(objectMap.size());

    }
    @Test
    public void pageTestEntity(){
//        Book book = new Book();
        Map<String,Object> objectMap = bookService.page(1,8,"springBoot");
        List<Book> bookList = (List<Book>)objectMap.get("pageContent");
        for (Book book1 : bookList) {
            System.out.println(book1.toString());
        }
        System.out.println("获取总记录数"+objectMap.get("TotalElements"));
    }

}
