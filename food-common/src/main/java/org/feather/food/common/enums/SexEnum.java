package org.feather.food.common.enums;

/**
 * @projectName: food
 * @package: org.feather.food.common.enums
 * @className: SexEnum
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/9/10 19:12
 * @version: 1.0
 */
public enum SexEnum {
    WOMAN(0,"女"),
    MAN(1,"男"),
    SECRET(2,"保密");


    private final  Integer type;


    private final String value;

    SexEnum(Integer type, String value) {
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
