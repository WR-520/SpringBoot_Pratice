package com.ln.springdemo.Repository;
import com.ln.springdemo.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * 1、基于命名规则： findByuserNameAndPassword
     *  public User login(String userName,String password)
     */
//    基于注解查询方式
    @Query(value = "select * from user where user_name=:strName and password=:password",nativeQuery = true)
    public User login(@Param("strName") String userName,@Param("password") String password);

    @Modifying
    @Transactional
    @Query(value = "update user set password=:password where id=:id", nativeQuery = true)
    public int updatePassword(@Param("id") Integer id,@Param("password") String password);
}
