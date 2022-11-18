package org.feather.food.vo;

/**
 * @projectName: food
 * @package: org.feather.food.vo
 * @className: OrderStatusCountsVO
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022-11-18 20:52
 * @version: 1.0
 */

public class OrderStatusCountsVO {
    private Integer waitPayCounts;
    private Integer waitDeliverCounts;
    private Integer waitReceiveCounts;
    private Integer waitCommentCounts;

    public OrderStatusCountsVO() {
    }

    public OrderStatusCountsVO(Integer waitPayCounts, Integer waitDeliverCounts, Integer waitReceiveCounts, Integer waitCommentCounts) {
        this.waitPayCounts = waitPayCounts;
        this.waitDeliverCounts = waitDeliverCounts;
        this.waitReceiveCounts = waitReceiveCounts;
        this.waitCommentCounts = waitCommentCounts;
    }

    public Integer getWaitPayCounts() {
        return waitPayCounts;
    }

    public void setWaitPayCounts(Integer waitPayCounts) {
        this.waitPayCounts = waitPayCounts;
    }

    public Integer getWaitDeliverCounts() {
        return waitDeliverCounts;
    }

    public void setWaitDeliverCounts(Integer waitDeliverCounts) {
        this.waitDeliverCounts = waitDeliverCounts;
    }

    public Integer getWaitReceiveCounts() {
        return waitReceiveCounts;
    }

    public void setWaitReceiveCounts(Integer waitReceiveCounts) {
        this.waitReceiveCounts = waitReceiveCounts;
    }

    public Integer getWaitCommentCounts() {
        return waitCommentCounts;
    }

    public void setWaitCommentCounts(Integer waitCommentCounts) {
        this.waitCommentCounts = waitCommentCounts;
    }
}
