package org.feather.food.vo;

import io.swagger.models.auth.In;

/**
 * @projectName: food
 * @package: org.feather.food.vo
 * @className: CommentLevelCounts
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/9/26 07:44
 * @version: 1.0
 */
public class CommentLevelCountsVO {
    private  Integer totalCounts;

    private Integer goodCounts;

    private Integer normalCounts;

    private Integer badCounts;

    public Integer getTotalCounts() {
        return totalCounts;
    }

    public void setTotalCounts(Integer totalCounts) {
        this.totalCounts = totalCounts;
    }

    public Integer getGoodCounts() {
        return goodCounts;
    }

    public void setGoodCounts(Integer goodCounts) {
        this.goodCounts = goodCounts;
    }

    public Integer getNormalCounts() {
        return normalCounts;
    }

    public void setNormalCounts(Integer normalCounts) {
        this.normalCounts = normalCounts;
    }

    public Integer getBadCounts() {
        return badCounts;
    }

    public void setBadCounts(Integer badCounts) {
        this.badCounts = badCounts;
    }
}
