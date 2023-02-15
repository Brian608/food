package org.feather.food.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @projectName: food
 * @package: org.feather.food.controller
 * @className: ItemsController
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2023-02-15 11:19
 * @version: 1.0
 */

@RestController
@RequestMapping("/items")
public class ItemsController {
    @GetMapping("/hello")
    public Object hello(){
        return  "hello Es";
    }
}
