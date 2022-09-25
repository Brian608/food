package org.feather.food;

import org.feather.food.pojo.Items;
import org.feather.food.pojo.ItemsImg;
import org.feather.food.pojo.ItemsParam;
import org.feather.food.pojo.ItemsSpec;

import java.util.List;

/**
 * @projectName: food
 * @package: org.feather.food
 * @className: ItemInfoVO
 * @author: feather(杜雪松)
 * @description:商品详情VO
 * @since: 2022/9/25 21:10
 * @version: 1.0
 */
public class ItemInfoVO {
     private Items items ;

     private List<ItemsImg> itemsImgs;

     private   List<ItemsSpec> itemsSpecs;

     private ItemsParam itemsParam;

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }

    public List<ItemsImg> getItemsImgs() {
        return itemsImgs;
    }

    public void setItemsImgs(List<ItemsImg> itemsImgs) {
        this.itemsImgs = itemsImgs;
    }

    public List<ItemsSpec> getItemsSpecs() {
        return itemsSpecs;
    }

    public void setItemsSpecs(List<ItemsSpec> itemsSpecs) {
        this.itemsSpecs = itemsSpecs;
    }

    public ItemsParam getItemsParam() {
        return itemsParam;
    }

    public void setItemsParam(ItemsParam itemsParam) {
        this.itemsParam = itemsParam;
    }
}
