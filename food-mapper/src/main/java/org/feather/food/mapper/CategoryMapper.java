package org.feather.food.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.feather.food.my.mapper.MyMapper;
import org.feather.food.pojo.Category;
@Mapper
public interface CategoryMapper extends MyMapper<Category> {
}