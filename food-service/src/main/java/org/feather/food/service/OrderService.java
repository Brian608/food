package org.feather.food.service;

import org.feather.food.bo.SubmitOrderBO;
import org.feather.food.pojo.OrderStatus;
import org.feather.food.vo.OrderVO;

/**
 * @projectName: food
 * @package: org.feather.food.service
 * @className: OrderService
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/10/10 21:21
 * @version: 1.0
 */
public interface OrderService {

    OrderVO createOrder(SubmitOrderBO submitOrderBO);

    /**
     * 修改订单状态
     * @param orderId
     * @param orderStatus
     */
    void updateOrderStatus(String orderId,Integer orderStatus);


    /**
     * 查询订单状态
     * @param orderId
     * @return
     */
    OrderStatus queryOrderStatusInfo(String orderId);

    /**
     * 关闭超时未支付订单
     */
    void closeOrder();

}
