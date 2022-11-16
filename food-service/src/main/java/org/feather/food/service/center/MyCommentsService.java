package org.feather.food.service.center;

import org.feather.food.bo.center.OrderItemsCommentBO;
import org.feather.food.common.utils.PagedGridResult;
import org.feather.food.pojo.OrderItems;

import java.util.List;

/**
 * @projectName: food
 * @package: org.feather.food.service.center
 * @className: MyCommentsService
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022-11-16 21:41
 * @version: 1.0
 */
public interface MyCommentsService {
    /**
     * 根据订单id查询关联的商品
     * @param orderId
     * @return
     */
     List<OrderItems> queryPendingComment(String orderId);


    void saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList);



    /**
     * 我的评价查询 分页
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
     PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize);
}
