package org.feather.food.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.feather.food.my.mapper.MyMapper;
import org.feather.food.pojo.ItemsComments;

@Mapper
public interface ItemsCommentsMapper extends MyMapper<ItemsComments> {
}