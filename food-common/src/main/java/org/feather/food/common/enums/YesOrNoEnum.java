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
public enum YesOrNoEnum {
    NO(0,"否"),
    YES(1,"是");

    private final  Integer type;


    private final String value;

    YesOrNoEnum(Integer type, String value) {
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
