package org.feather.food.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.feather.food.bo.ShopCartBO;
import org.feather.food.bo.SubmitOrderBO;
import org.feather.food.common.enums.OrderStatusEnum;
import org.feather.food.common.enums.PayMethodEnum;
import org.feather.food.common.utils.CookieUtils;
import org.feather.food.common.utils.JSONResult;
import org.feather.food.common.utils.JsonUtils;
import org.feather.food.common.utils.RedisOperator;
import org.feather.food.pojo.OrderStatus;
import org.feather.food.service.OrderService;
import org.feather.food.vo.MerchantOrdersVO;
import org.feather.food.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisOperator redisOperator;

    @ApiOperation(value = "用户下单",notes = "用户下单",httpMethod = "POST")
    @PostMapping("/create")
    public JSONResult create(@RequestBody  SubmitOrderBO submitOrderBO, HttpServletRequest request, HttpServletResponse response){

        if (submitOrderBO.getPayMethod()!= PayMethodEnum.WEIXIN.getType()&&submitOrderBO.getPayMethod()!=PayMethodEnum.ALIPAY.getType()){
            return  JSONResult.errorMsg("支付方式不对");
        }
        String shopCartJson = redisOperator.get(FOODIE_SHOPCART + ":"+submitOrderBO.getUserId());
        if (StringUtils.isBlank(shopCartJson)){
            return  JSONResult.errorMsg("购物车数据不正确");
        }
        List<ShopCartBO> shopCartBOList = JsonUtils.jsonToList(shopCartJson, ShopCartBO.class);

        //1 创建订单
        OrderVO orderVO = orderService.createOrder(shopCartBOList,submitOrderBO);
        String orderId = orderVO.getOrderId();
        MerchantOrdersVO merchantOrdersVO = orderVO.getMerchantOrdersVO();
        merchantOrdersVO.setReturnUrl(payReturnUrl);
        // 2创建订单以后 移除购物车中已结算的商品

        //清理覆盖现有的redis汇总购物车数据
        shopCartBOList.removeAll(orderVO.getToBeRemovedShopcatList());
        redisOperator.set(FOODIE_SHOPCART+":"+submitOrderBO.getUserId(),JsonUtils.objectToJson(shopCartBOList));
        // 整合redis之后，完善购物车中的已结算的商品清除，并且同步到前端的cookie
        CookieUtils.setCookie(request,response,"shopcart",JsonUtils.objectToJson(shopCartBOList),true);
        //3  向支付中心发送当前订单，用户保存支付中心的订单数据

        //设置为一分钱为了测试
        merchantOrdersVO.setAmount(1);

        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("userId","duxuesong");
        httpHeaders.add("password","123456");
        HttpEntity<MerchantOrdersVO> entity=new HttpEntity<>(merchantOrdersVO,httpHeaders);
        ResponseEntity<JSONResult> jsonResultResponseEntity = restTemplate.postForEntity(paymentUrl, entity, JSONResult.class);
        JSONResult paymentResult = jsonResultResponseEntity.getBody();
        if (paymentResult.getcode()!=200){
            return  JSONResult.errorMsg("支付中心订单创建失败，请联系管理员");
        }
        return  JSONResult.ok(orderId);
    }

    @PostMapping("/notifyMerchantOrderPaid")
    public Integer   notifyMerchantOrderPaid(String merchantOrderId){
        orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_PAY.type);
        return HttpStatus.OK.value();
    }

    @PostMapping("/getPaidOrderInfo")
    public JSONResult   getPaidOrderInfo(String orderId){
        OrderStatus orderStatus = orderService.queryOrderStatusInfo(orderId);
        return JSONResult.ok(orderStatus);
    }
}
