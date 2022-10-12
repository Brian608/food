package org.feather.food.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.feather.food.my.mapper.MyMapper;
import org.feather.food.pojo.OrderStatus;

@Mapper
public interface OrderStatusMapper extends MyMapper<OrderStatus> {
}