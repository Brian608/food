package org.feather.food.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.feather.food.pojo.OrderStatus;
import org.feather.food.vo.MyOrdersVO;

import java.util.List;
import java.util.Map;

/**
 * @projectName: food
 * @package: org.feather.food.mapper
 * @className: OrdersCustomMapper
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022-11-11 21:57
 * @version: 1.0
 */
@Mapper
public interface OrdersCustom {

    /**
     * 查询我的订单
     * @param map
     * @return
     */
     List<MyOrdersVO> queryMyOrders(@Param("paramsMap") Map<String,Object> map);


     int getMyOrderStatusCounts(@Param("paramsMap") Map<String,Object> map);


    List<OrderStatus> getMyOrderTrend(@Param("paramsMap") Map<String, Object> map);

}
