package org.feather.food.vo;

/**
 * @projectName: food
 * @package: org.feather.food.vo
 * @className: SearchItemsVO
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/9/29 07:19
 * @version: 1.0
 */
public class SearchItemsVO {
    private  String itemId;

    private String itemName;

    private  Integer sellCounts;

    private String imgUrl;

    private Integer price;


    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getSellCounts() {
        return sellCounts;
    }

    public void setSellCounts(Integer sellCounts) {
        this.sellCounts = sellCounts;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
