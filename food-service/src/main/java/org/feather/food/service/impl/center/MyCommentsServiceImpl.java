package org.feather.food.service.impl.center;

import com.github.pagehelper.PageHelper;
import org.feather.food.bo.center.OrderItemsCommentBO;
import org.feather.food.common.enums.YesOrNoEnum;
import org.feather.food.common.utils.PagedGridResult;
import org.feather.food.mapper.ItemsCommentsMapperCustom;
import org.feather.food.mapper.OrderItemsMapper;
import org.feather.food.mapper.OrderStatusMapper;
import org.feather.food.mapper.OrdersMapper;
import org.feather.food.pojo.OrderItems;
import org.feather.food.pojo.OrderStatus;
import org.feather.food.pojo.Orders;
import org.feather.food.service.center.MyCommentsService;
import org.feather.food.service.impl.BaseService;
import org.feather.food.vo.MyCommentVO;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @projectName: food
 * @package: org.feather.food.service.impl.center
 * @className: MyCommentsServiceImpl
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022-11-16 21:42
 * @version: 1.0
 */

@Service
public class MyCommentsServiceImpl  extends BaseService implements MyCommentsService {

    @Autowired
    private Sid sid;

    @Autowired
    public OrderItemsMapper orderItemsMapper;

    @Autowired
    public OrdersMapper ordersMapper;

    @Autowired
    public ItemsCommentsMapperCustom itemsCommentsMapperCustom;

    @Autowired
    public OrderStatusMapper orderStatusMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<OrderItems> queryPendingComment(String orderId) {
        OrderItems query = new OrderItems();
        query.setOrderId(orderId);
        return orderItemsMapper.select(query);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveComments(String orderId, String userId,
                             List<OrderItemsCommentBO> commentList) {

        // 1. 保存评价 items_comments
        for (OrderItemsCommentBO oic : commentList) {
            oic.setCommentId(sid.nextShort());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("commentList", commentList);
        itemsCommentsMapperCustom.saveComments(map);

        // 2. 修改订单表改已评价 orders
        Orders order = new Orders();
        order.setId(orderId);
        order.setIsComment(YesOrNoEnum.YES.getType());
        ordersMapper.updateByPrimaryKeySelective(order);

        // 3. 修改订单状态表的留言时间 order_status
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCommentTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyComments(String userId,
                                           Integer page,
                                           Integer pageSize) {

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        PageHelper.startPage(page, pageSize);
        List<MyCommentVO> list = itemsCommentsMapperCustom.queryMyComments(map);
        return setterPagedGrid(list, page);
    }

}
