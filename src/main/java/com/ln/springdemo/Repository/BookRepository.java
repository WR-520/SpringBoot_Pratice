package com.ln.springdemo.Repository;

import com.ln.springdemo.bean.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


//分页排序操作
@Repository
//JpaSecificationExecutor一般要和Repository体系的接口
//一起使用，用于动态生成Query来满足业务中各种复杂场景
public interface BookRepository extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {

}


//@Repository
//public interface BookRepository extends CrudRepository<Book,Integer> {
//    //方法名称命名查询
//    //增加ID和书名进行查询
//
//    /**
//     * 方法名称必须要遵循驼峰式命名规则：
//     *findBy（关键字）+属性名称（首字母大写）+查询条件（首字母大写
//     */
//    //select * from book where id=? and name=?
//    public Book findByIdAndName(int id, String name);
//
//    //根据书名进行模糊查询
//    public List<Book> findByNameLike(String name);
//
//    //基于注解的查询方式 使用JPQL语句查询，基于实体类的查询来进行数据库表的映射
//
//    /**
//     *基于@query注解操作中，如果涉及到删除和修改操作需要加上@Modifying，
//     * 也可以根据需要添加@Transactional对事务的支持
//     */
//    @Transactional
//    @Modifying
//    //@Query中的nativeQuery = true 表示基于数据库表的SQL语句的操作
//    @Query("update Book b set b.name =?1 where b.id=?2")
//    int updateBookById(String name,int id);
//
//    //基于注解的方式来完成根据id删除操作//基于原始SQL
////    @Transactional实质是使用了JDBC的事务来进行事务控制
////    @Transactional基于Spring的动态代理机制
//
//    @Transactional
//    @Modifying
//    @Query(value="delete from Book where id=?1",nativeQuery = true)
//    int deleteBookById(int id);
//
//    /**
//     * sql语句中传参的两种方式
//     * 1、使用？，紧跟数字序列，数字序列从1开始
//     * 2、使用：, 紧跟参数名，参数名通过@param注解来标识
//     */
//     @Query(value = "select * from book where name=:strName",nativeQuery = true)
//     Book findByBook(@Param("strName") String name);
//
//     @Query(value = "select * from book where name = ?1 and author = ?2",nativeQuery = true)
//     Book findByNameAndAuthor(@Param("name") String name, @Param("author") String author);
//}
