package org.feather.food.common.enums;

/**
 * @projectName: food
 * @package: org.feather.food.common.enums
 * @className: YesOrNoEnum
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/9/24 21:28
 * @version: 1.0
 */
public enum CommentLevelEnum {
    GOOD(1,"好评"),
    NORMAL(2,"中评"),
    BAD(3,"差评");


    private final  Integer type;


    private final String value;

    CommentLevelEnum(Integer type, String value) {
        this.type = type;
        this.value = value;
    }

    public Integer getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

}
