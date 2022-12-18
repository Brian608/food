package org.feather.food.vo;

import org.feather.food.bo.ShopCartBO;

import java.util.List;

/**
 * @projectName: food
 * @package: org.feather.food.vo
 * @className: OrderVO
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/10/22 21:32
 * @version: 1.0
 */
public class OrderVO {

    private String orderId;
    private MerchantOrdersVO merchantOrdersVO;

    private List<ShopCartBO> toBeRemovedShopCatList;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public MerchantOrdersVO getMerchantOrdersVO() {
        return merchantOrdersVO;
    }

    public void setMerchantOrdersVO(MerchantOrdersVO merchantOrdersVO) {
        this.merchantOrdersVO = merchantOrdersVO;
    }

    public List<ShopCartBO> getToBeRemovedShopcatList() {
        return toBeRemovedShopCatList;
    }

    public void setToBeRemovedShopcatList(List<ShopCartBO> toBeRemovedShopCatList) {
        this.toBeRemovedShopCatList = toBeRemovedShopCatList;
    }
}
