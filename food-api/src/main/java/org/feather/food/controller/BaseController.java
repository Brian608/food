package org.feather.food.controller;

import org.springframework.web.bind.annotation.RestController;

/**
 * @projectName: food
 * @package: org.feather.food.controller
 * @className: BaseController
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/9/26 21:44
 * @version: 1.0
 */
@RestController
public class BaseController {

    private static  final String FOODIE_SHOPCART="shopcart";

    public static  final Integer PAGE_SIZE=10;
}
