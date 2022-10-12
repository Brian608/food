package org.feather.food.common.enums;

import io.swagger.models.auth.In;

/**
 * @projectName: food
 * @package: org.feather.food.common.enums
 * @className: PayMethod
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/10/10 21:11
 * @version: 1.0
 */

public enum PayMethodEnum {
    WEIXIN(1,"微信"),
    ALIPAY(2,"支付宝");


    private final  Integer type;


    private final String value;


    PayMethodEnum(Integer type, String value) {
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
