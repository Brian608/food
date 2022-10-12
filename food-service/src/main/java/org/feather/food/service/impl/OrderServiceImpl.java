package org.feather.food.service.impl;

import org.feather.food.bo.SubmitOrderBO;
import org.feather.food.common.enums.OrderStatusEnum;
import org.feather.food.common.enums.YesOrNoEnum;
import org.feather.food.mapper.OrderItemsMapper;
import org.feather.food.mapper.OrderStatusMapper;
import org.feather.food.mapper.OrdersMapper;
import org.feather.food.pojo.*;
import org.feather.food.service.AddressService;
import org.feather.food.service.ItemService;
import org.feather.food.service.OrderService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @projectName: food
 * @package: org.feather.food.service.impl
 * @className: OrderServiceImpl
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/10/10 21:21
 * @version: 1.0
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Autowired
    private Sid sid;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ItemService itemService;


    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public String createOrder(SubmitOrderBO submitOrderBO) {
        String userId = submitOrderBO.getUserId();
        String addressId = submitOrderBO.getAddressId();
        String itemSpecIds = submitOrderBO.getItemSpecIds();
        Integer payMethod = submitOrderBO.getPayMethod();
        String leftMsg = submitOrderBO.getLeftMsg();
        //包邮费用设置为0
        Integer postAmount=0;

        String orderId = sid.nextShort();
        UserAddress userAddress = addressService.queryUserAddress(userId, addressId);
        //1。新订单数据保存
        Orders orders=new Orders();
        orders.setId(orderId);
        orders.setUserId(userId);
        orders.setReceiverAddress(userAddress.getProvince()+" "+userAddress.getCity()+" "+userAddress.getDistrict()+" "+userAddress.getDetail());
        orders.setReceiverName(userAddress.getReceiver());
        orders.setReceiverMobile(userAddress.getMobile());
        orders.setPostAmount(postAmount);
        orders.setPayMethod(payMethod);
        orders.setLeftMsg(leftMsg);
        orders.setIsComment(YesOrNoEnum.NO.getType());
        orders.setIsDelete(YesOrNoEnum.NO.getType());
        orders.setCreatedTime(new Date());
        orders.setUpdatedTime(new Date());

        //2.循环itemSpecIds保存订单商品信息表
        String[] itemSpecIdArr = itemSpecIds.split(",");
        //商品原价累计
        Integer totalAmount=0;
        //优惠后的实际支付价格累计
        Integer realPayAount=0;
        for (String itemSpecId : itemSpecIdArr) {
            //TODO  整合热地说后，商品购买的数量重新从redis的购物车中获取
            int buyCounts=1;
            //2。1 根据规格ID 查询规格的具体信息，主要获取价格
            ItemsSpec itemsSpec = itemService.queryItemSpecById(itemSpecId);
            totalAmount+=itemsSpec.getPriceNormal()  *buyCounts;
            realPayAount+=itemsSpec.getPriceDiscount()*buyCounts;
            //2。2根据规格ID获取商品信息以及商品图片
            String itemId = itemsSpec.getItemId();
            Items items = itemService.queryItemById(itemId);
            String imgUrl = itemService.queryItemMainImgById(itemId);
            //2.3循环保存自订单到数据库
            String subOrderId=sid.nextShort();
            OrderItems subOrderItem=new OrderItems();
            subOrderItem.setId(subOrderId);
            subOrderItem.setOrderId(orderId);
            subOrderItem.setItemId(itemId);
            subOrderItem.setItemName(items.getItemName());
            subOrderItem.setItemImg(imgUrl);
            subOrderItem.setBuyCounts(buyCounts);
            subOrderItem.setItemSpecId(itemSpecId);
            subOrderItem.setItemSpecName(itemsSpec.getName());
            subOrderItem.setPrice(itemsSpec.getPriceDiscount());
            orderItemsMapper.insert(subOrderItem);
            //2.4在用户提交订单以后，规格表中需要扣减库存
           itemService.decreaseItemSpecStock(itemSpecId,buyCounts);
        }
        orders.setTotalAmount(totalAmount);
        orders.setRealPayAmount(realPayAount);
        ordersMapper.insert(orders);
        //3.保存订单状态表
        OrderStatus waiPayOrderStatus=new OrderStatus();
        waiPayOrderStatus.setOrderId(orderId);
        waiPayOrderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        waiPayOrderStatus.setCreatedTime(new Date());
        orderStatusMapper.insert(waiPayOrderStatus);
        return  orderId;
    }
}