package org.feather.food.controller;

import org.feather.food.common.utils.JSONResult;
import org.feather.food.common.utils.RedisOperator;
import org.feather.food.pojo.Orders;
import org.feather.food.pojo.Users;
import org.feather.food.service.center.MyOrdersService;
import org.feather.food.vo.UsersVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.UUID;

/**
 * @projectName: food
 * @package: org.feather.food.controller
 * @className: BaseController
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/9/26 21:44
 * @version: 1.0
 */
@Controller
public class BaseController {

    @Autowired
    public MyOrdersService myOrdersService;

    @Autowired
    private RedisOperator redisOperator;

    public static  final String FOODIE_SHOPCART="shopcart";

    public static  final Integer COMMON_PAGE_SIZE=10;

    public static  final String  REDIS_USER_TOKEN="redis_user_token";

    //微信支付成功--》支付中心-----》天天吃货平台
    //                  回调通知的url
   public  static final String payReturnUrl="http://localhost:8088/orders/notifyMerchantOrderPaid";

    /**
     * 支付中心地址
     */
   public static final String paymentUrl="http://ssssssxxxxxxx";

    /**
     * 用户头像保存的地址
     */

  // public static  final String IMAGE_USER_FACE_LOCATION="D:\\dev\\files\\images\\food-dev\\faces";

    public static  final String IMAGE_USER_FACE_LOCATION="D:"+ File.separator+"dev"+File.separator+"files"+File.separator+"images"+
            File.separator+"food-dev"+File.separator+"faces";


    /**
     * 用于验证用户和订单是否有关联关系，避免非法用户调用
     * @return
     */
    public JSONResult checkUserOrder(String userId, String orderId) {
        Orders order = myOrdersService.queryMyOrder(userId, orderId);
        if (order == null) {
            return JSONResult.errorMsg("订单不存在！");
        }
        return JSONResult.ok();
    }

    public UsersVO conventUsersVO(Users user) {
        // 实现用户的redis会话
        String uniqueToken = UUID.randomUUID().toString().trim();
        redisOperator.set(REDIS_USER_TOKEN + ":" + user.getId(),
                uniqueToken);

        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(user, usersVO);
        usersVO.setUserUniqueToken(uniqueToken);
        return usersVO;
    }
}
