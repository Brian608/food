package org.feather.food.service.center;

import org.feather.food.common.utils.PagedGridResult;
import org.feather.food.pojo.Orders;

/**
 * @projectName: food
 * @package: org.feather.food.service.center
 * @className: MyOrdersService
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022-11-11 22:33
 * @version: 1.0
 */
public interface MyOrdersService {
    /**
     * 查询我的订单列表
     *
     * @param userId
     * @param orderStatus
     * @param page
     * @param pageSize
     * @return
     */
     PagedGridResult queryMyOrders(String userId,
                                         Integer orderStatus,
                                         Integer page,
                                         Integer pageSize);
    /**
     * @Description: 订单状态 --> 商家发货
     */
     void updateDeliverOrderStatus(String orderId);


    /**
     * 查询我的订单
     *
     * @param userId
     * @param orderId
     * @return
     */
     Orders queryMyOrder(String userId, String orderId);

    /**
     * 更新订单状态 —> 确认收货
     *
     * @return
     */
     boolean updateReceiveOrderStatus(String orderId);

    /**
     * 删除订单（逻辑删除）
     * @param userId
     * @param orderId
     * @return
     */
     boolean deleteOrder(String userId, String orderId);

}
