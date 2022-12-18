package org.feather.food.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.feather.food.bo.ShopCartBO;
import org.feather.food.bo.SubmitOrderBO;
import org.feather.food.common.enums.OrderStatusEnum;
import org.feather.food.common.enums.YesOrNoEnum;
import org.feather.food.common.utils.DateUtil;
import org.feather.food.mapper.OrderItemsMapper;
import org.feather.food.mapper.OrderStatusMapper;
import org.feather.food.mapper.OrdersMapper;
import org.feather.food.pojo.*;
import org.feather.food.service.AddressService;
import org.feather.food.service.ItemService;
import org.feather.food.service.OrderService;
import org.feather.food.vo.MerchantOrdersVO;
import org.feather.food.vo.OrderVO;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public OrderVO createOrder(List<ShopCartBO> shopCartBOList,SubmitOrderBO submitOrderBO) {
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
        List<ShopCartBO> toBeRemovedShopcatList=new ArrayList<>();
        for (String itemSpecId : itemSpecIdArr) {
            //  整合redis后，商品购买的数量重新从redis的购物车中获取
            ShopCartBO buyCountsFromShopCart = getBuyCountsFromShopCart(shopCartBOList, itemSpecId);
            int buyCounts=buyCountsFromShopCart.getBuyCounts();
            toBeRemovedShopcatList.add(buyCountsFromShopCart);
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

        //构建商户订单  用于传给支付中心
        MerchantOrdersVO merchantOrdersVO=new MerchantOrdersVO();
        merchantOrdersVO.setMerchantOrderId(orderId);
        merchantOrdersVO.setMerchantUserId(userId);
        merchantOrdersVO.setAmount(realPayAount+postAmount);
        merchantOrdersVO.setPayMethod(payMethod);
        //构建自定义订单vo
        OrderVO orderVO=new OrderVO();
        orderVO.setOrderId(orderId);
        orderVO.setMerchantOrdersVO(merchantOrdersVO);
        orderVO.setToBeRemovedShopcatList(toBeRemovedShopcatList);
        return  orderVO;
    }

    /**
     * 从redis中的购物车获取商品，目的 counts
     * @param shopCartBOList
     * @param specId
     * @return
     */
    private  ShopCartBO getBuyCountsFromShopCart(List<ShopCartBO> shopCartBOList, String specId){
        for (ShopCartBO shopCartBO : shopCartBOList) {
            if (shopCartBO.getSpecId().equals(specId)){
                return shopCartBO;
            }
        }
        return  null;

    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public void updateOrderStatus(String orderId, Integer orderStatus) {
        OrderStatus paidStatus=new OrderStatus();
        paidStatus.setOrderId(orderId);
        paidStatus.setOrderStatus(orderStatus);
        paidStatus.setPayTime(new Date());
        orderStatusMapper.updateByPrimaryKey(paidStatus);
    }

    @Override
    public OrderStatus queryOrderStatusInfo(String orderId) {
        return orderStatusMapper.selectByPrimaryKey(orderId);
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public void closeOrder() {
        //查询未付款订单，判断时间是否超时(1天） ，超时则关闭交易
        OrderStatus orderStatus=new OrderStatus();
        orderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        List<OrderStatus> list = orderStatusMapper.select(orderStatus);
        if (!list.isEmpty()){
            for (OrderStatus os : list) {
                //获得订单创建时间
                Date createdTime = os.getCreatedTime();
                int daysBetween = DateUtil.daysBetween(createdTime, new Date());
                if (daysBetween>=1){
                    //超过一天，关闭订单
                    doCloseOrder(os.getOrderId());
                }
            }
        }

    }

    @Transactional(propagation = Propagation.REQUIRED)
    void  doCloseOrder(String orderId){
        OrderStatus close=new OrderStatus();
        close.setOrderStatus(OrderStatusEnum.CLOSE.type);
        close.setOrderId(orderId);
        close.setCloseTime(new Date());
        orderStatusMapper.updateByPrimaryKey(close);
    }
}
