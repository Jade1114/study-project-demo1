package org.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.entity.Account;

@Mapper
public interface UserMapper {
    @Select("select * from db_account where username = #{test} or email = #{text}")
    Account findAccountByNameOrEmail(String text);

    @Insert("insert into db_account (username,password,email) values (#{username}, #{password}, #{email})")
    int createAccount(String username,String password,String email);
}
