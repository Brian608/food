package org.feather.food.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

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

    private static  final String FOODIE_SHOPCART="shopcart";

    public static  final Integer PAGE_SIZE=10;

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
}
