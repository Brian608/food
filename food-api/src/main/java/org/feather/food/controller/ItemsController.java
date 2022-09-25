package org.feather.food.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.feather.food.ItemInfoVO;
import org.feather.food.common.utils.JSONResult;
import org.feather.food.pojo.Items;
import org.feather.food.pojo.ItemsImg;
import org.feather.food.pojo.ItemsParam;
import org.feather.food.pojo.ItemsSpec;
import org.feather.food.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @projectName: food
 * @package: org.feather.food.controller
 * @className: ItemsController
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/9/25 21:03
 * @version: 1.0
 */
@Api(value = "商品接口",tags = {"商品信息展示相关接口"})
@RequestMapping("/items")
@RestController
public class ItemsController {
    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "查询商品详情",notes = "查询商品详情",httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public JSONResult info(@ApiParam(name = "itemId",value = "商品ID",required = true) @PathVariable String itemId){
        if (StringUtils.isBlank(itemId)){
            return  JSONResult.errorMsg(null);
        }
        Items items = itemService.queryItemById(itemId);
        List<ItemsImg> itemsImgs = itemService.queryItemImgList(itemId);
        List<ItemsSpec> itemsSpecs = itemService.queryItemSpecList(itemId);
        ItemsParam itemsParam = itemService.queryItemParam(itemId);
        ItemInfoVO itemInfoVO=new ItemInfoVO();
        itemInfoVO.setItems(items);
        itemInfoVO.setItemsImgs(itemsImgs);
        itemInfoVO.setItemsSpecs(itemsSpecs);
        itemInfoVO.setItemsParam(itemsParam);
        return  JSONResult.ok(itemInfoVO);

    }
}
