package org.feather.food.vo;

import java.util.List;

/**
 * @projectName: food
 * @package: org.feather.food.vo
 * @className: myNewItemsVO
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/9/25 15:55
 * @version: 1.0
 */
public class NewItemsVO {
    private Integer rootCatId;

    private String rootCatName;

    private String slogan;

    private String catImage;

    private String bgColor;

    private List<SimpleItemVO> simpleItemVOList;

    public Integer getRootCatId() {
        return rootCatId;
    }

    public void setRootCatId(Integer rootCatId) {
        this.rootCatId = rootCatId;
    }

    public String getRootCatName() {
        return rootCatName;
    }

    public void setRootCatName(String rootCatName) {
        this.rootCatName = rootCatName;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getCatImage() {
        return catImage;
    }

    public void setCatImage(String catImage) {
        this.catImage = catImage;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public List<SimpleItemVO> getSimpleItemVOList() {
        return simpleItemVOList;
    }

    public void setSimpleItemVOList(List<SimpleItemVO> simpleItemVOList) {
        this.simpleItemVOList = simpleItemVOList;
    }
}
