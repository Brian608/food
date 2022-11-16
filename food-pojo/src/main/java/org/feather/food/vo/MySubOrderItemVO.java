package org.feather.food.vo;

/**
 * @projectName: food
 * @package: org.feather.food.vo
 * @className: MySubOrderItemVO
 * @author: feather(杜雪松)
 * @description:  用户中心，我的订单列表嵌套商品VO
 * @since: 2022-11-11 22:26
 * @version: 1.0
 */

public class MySubOrderItemVO {
    private String itemId;
    private String itemImg;
    private String itemName;

    private String itemSpecId;
    private String itemSpecName;
    private Integer buyCounts;
    private Integer price;

    public String getItemSpecId() {
        return itemSpecId;
    }

    public void setItemSpecId(String itemSpecId) {
        this.itemSpecId = itemSpecId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemImg() {
        return itemImg;
    }

    public void setItemImg(String itemImg) {
        this.itemImg = itemImg;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemSpecName() {
        return itemSpecName;
    }

    public void setItemSpecName(String itemSpecName) {
        this.itemSpecName = itemSpecName;
    }

    public Integer getBuyCounts() {
        return buyCounts;
    }

    public void setBuyCounts(Integer buyCounts) {
        this.buyCounts = buyCounts;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
