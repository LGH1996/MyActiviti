package com.lgh.activiti;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MyBeanMapper {
    @Select("SELECT * FROM test WHERE NAME=#{name}")
    MyBean selectByName(String name);
}
