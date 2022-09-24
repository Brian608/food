package org.feather.food.vo;

/**
 * @projectName: food
 * @package: org.feather.food.vo
 * @className: SubCategoryVO
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/9/24 22:54
 * @version: 1.0
 */
public class SubCategoryVO {
    private  Integer subId;

    private String subName;

    private String subType;

    private String subFatherId;

    public Integer getSubId() {
        return subId;
    }

    public void setSubId(Integer subId) {
        this.subId = subId;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getSubFatherId() {
        return subFatherId;
    }

    public void setSubFatherId(String subFatherId) {
        this.subFatherId = subFatherId;
    }
}
