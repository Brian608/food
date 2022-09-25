package org.feather.food.vo;

/**
 * @projectName: food
 * @package: org.feather.food.vo
 * @className: SimpleItemVO
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/9/25 18:01
 * @version: 1.0
 */
public class SimpleItemVO {
    private String itemId;

    private String  itemName;
    private  String  itemUrl;

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

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }
}
