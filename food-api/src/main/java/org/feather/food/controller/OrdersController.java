package org.feather.food.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.feather.food.bo.SubmitOrderBO;
import org.feather.food.common.enums.PayMethodEnum;
import org.feather.food.common.utils.CookieUtils;
import org.feather.food.common.utils.JSONResult;
import org.feather.food.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @projectName: food
 * @package: org.feather.food.controller
 * @className: OrdersController
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/10/9 22:07
 * @version: 1.0
 */
@Api(value = "订单相关",tags = {"订单相关的api接口"})
@RequestMapping("/orders")
@RestController
public class OrdersController extends BaseController {

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "用户下单",notes = "用户下单",httpMethod = "POST")
    @PostMapping("/create")
    public JSONResult create(@RequestBody  SubmitOrderBO submitOrderBO, HttpServletRequest request, HttpServletResponse response){

        if (submitOrderBO.getPayMethod()!= PayMethodEnum.WEIXIN.getType()&&submitOrderBO.getPayMethod()!=PayMethodEnum.ALIPAY.getType()){
            return  JSONResult.errorMsg("支付方式不对");
        }
        //1 创建订单
        String orderId = orderService.createOrder(submitOrderBO);
        // 2 移除购物车中已结算的商品
        //todo 整合redis之后，完善购物车中的已结算的商品清除，并且同步到前端的cookie
       // CookieUtils.setCookie(request,response,"shopcart","",true);
        //3  向支付中心发送当前订单，用户保存支付中心的订单数据

        return  JSONResult.ok(orderId);
    }
}
