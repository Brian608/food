package org.feather.food.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.feather.food.bo.ShopCartBO;
import org.feather.food.common.utils.JSONResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @projectName: food
 * @package: org.feather.food.controller
 * @className: ShopCatController
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/10/1 21:56
 * @version: 1.0
 */
@Api(value = "购物车相关接口",tags = {"购物车相关接口"})
@RequestMapping("/shopcart")
@RestController
public class ShopCartController {



    @ApiOperation(value = "添加商品到购物车🛒",notes = "添加商品到购物车",httpMethod = "POST")
    @PostMapping("/add")
    public JSONResult add(@RequestParam String userId,
                          @RequestBody ShopCartBO shopCartBO,
                          HttpServletRequest request,
                          HttpServletResponse response){
        if (StringUtils.isBlank(userId)){
            return JSONResult.errorMsg("");
        }
        //TODO  前端用户在登录的情况下，添加商品到购物车，会同时在后端同步购物车到redis

            return  JSONResult.ok();

    }
    @ApiOperation(value = "从购物车删除商品🛒",notes = "从购物车删除商品",httpMethod = "POST")
    @PostMapping("/del")
    public JSONResult del(@RequestParam String userId,
                          @RequestParam String itemSpecId,
                          @RequestBody ShopCartBO shopCartBO,
                          HttpServletRequest request,
                          HttpServletResponse response){
        if (StringUtils.isBlank(userId)||StringUtils.isBlank(itemSpecId)){
            return JSONResult.errorMsg("");
        }
        //TODO  用户在页面删除购物车中的商品数据，如果此时用户已经登录，则需要同步删除购物车中的商品数据

        return  JSONResult.ok();

    }
}
