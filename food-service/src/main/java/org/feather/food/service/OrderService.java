package org.feather.food.service;

import org.feather.food.bo.SubmitOrderBO;

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

    String createOrder(SubmitOrderBO submitOrderBO);

}
