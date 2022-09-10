package org.feather.food.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.feather.food.my.mapper.MyMapper;
import org.feather.food.pojo.Users;
import tk.mybatis.spring.annotation.MapperScan;

@Mapper
public interface UsersMapper extends MyMapper<Users> {
}