package org.feather.food.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.feather.food.bo.ShopCartBO;
import org.feather.food.common.utils.JSONResult;
import org.feather.food.common.utils.JsonUtils;
import org.feather.food.common.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

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
public class ShopCartController extends  BaseController {

    @Autowired
    private RedisOperator redisOperator;



    @ApiOperation(value = "添加商品到购物车🛒",notes = "添加商品到购物车",httpMethod = "POST")
    @PostMapping("/add")
    public JSONResult add(@RequestParam String userId,
                          @RequestBody ShopCartBO shopCartBO,
                          HttpServletRequest request,
                          HttpServletResponse response){
        if (StringUtils.isBlank(userId)){
            return JSONResult.errorMsg("");
        }
        // 前端用户在登录的情况下，添加商品到购物车，会同时在后端同步购物车到redis缓存
        // 需要判断当前购物车中包含已经存在的商品，如果存在则累加购买数量
        String shopcartJson = redisOperator.get(FOODIE_SHOPCART + ":" + userId);
        List<ShopCartBO> shopcartList = null;
        if (StringUtils.isNotBlank(shopcartJson)) {
            // redis中已经有购物车了
            shopcartList = JsonUtils.jsonToList(shopcartJson, ShopCartBO.class);
            // 判断购物车中是否存在已有商品，如果有的话counts累加
            boolean isHaving = false;
            for (ShopCartBO sc: shopcartList) {
                String tmpSpecId = sc.getSpecId();
                if (tmpSpecId.equals(shopCartBO.getSpecId())) {
                    sc.setBuyCounts(sc.getBuyCounts() + shopCartBO.getBuyCounts());
                    isHaving = true;
                }
            }
            if (!isHaving) {
                shopcartList.add(shopCartBO);
            }
        } else {
            // redis中没有购物车
            shopcartList = new ArrayList<>();
            // 直接添加到购物车中
            shopcartList.add(shopCartBO);
        }

        // 覆盖现有redis中的购物车
        redisOperator.set(FOODIE_SHOPCART + ":" + userId, JsonUtils.objectToJson(shopcartList));


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
        // 用户在页面删除购物车中的商品数据，如果此时用户已经登录，则需要同步删除redis购物车中的商品
        String shopcartJson = redisOperator.get(FOODIE_SHOPCART + ":" + userId);
        if (StringUtils.isNotBlank(shopcartJson)) {
            // redis中已经有购物车了
            List<ShopCartBO> shopcartList = JsonUtils.jsonToList(shopcartJson, ShopCartBO.class);
            // 判断购物车中是否存在已有商品，如果有的话则删除
            for (ShopCartBO sc: shopcartList) {
                String tmpSpecId = sc.getSpecId();
                if (tmpSpecId.equals(itemSpecId)) {
                    shopcartList.remove(sc);
                    break;
                }
            }
            // 覆盖现有redis中的购物车
            redisOperator.set(FOODIE_SHOPCART + ":" + userId, JsonUtils.objectToJson(shopcartList));
        }

        return  JSONResult.ok();

    }
}
