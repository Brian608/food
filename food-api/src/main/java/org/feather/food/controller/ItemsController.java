package org.feather.food.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.feather.food.ItemInfoVO;
import org.feather.food.common.utils.JSONResult;
import org.feather.food.common.utils.PagedGridResult;
import org.feather.food.pojo.Items;
import org.feather.food.pojo.ItemsImg;
import org.feather.food.pojo.ItemsParam;
import org.feather.food.pojo.ItemsSpec;
import org.feather.food.service.ItemService;
import org.feather.food.vo.CommentLevelCountsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
public class ItemsController  extends  BaseController{
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

    @ApiOperation(value = "查询商品评价等级",notes = "查询商品评价等级",httpMethod = "GET")
    @GetMapping("/commentLevel")
    public JSONResult commentLevel(@ApiParam(name = "itemId",value = "商品ID",required = true) @RequestParam String itemId){
        if (StringUtils.isBlank(itemId)){
            return  JSONResult.errorMsg(null);
        }
        CommentLevelCountsVO commentLevelCountsVO = itemService.queryCommentCounts(itemId);
        return  JSONResult.ok(commentLevelCountsVO);

    }

    @ApiOperation(value = "查询商品评论",notes = "查询商品评论",httpMethod = "GET")
    @GetMapping("/comments")
    public JSONResult comments(@ApiParam(name = "itemId",value = "商品ID",required = true) @RequestParam String itemId,
                               @ApiParam(name = "level",value = "评价等级") @RequestParam Integer level,
                               @ApiParam(name = "page",value = "查询下一页的第几页") @RequestParam Integer page,
                               @ApiParam(name = "pageSize",value = "分页的每一页显示的条数") @RequestParam Integer pageSize){
        if (StringUtils.isBlank(itemId)){
            return  JSONResult.errorMsg(null);
        }
        if (page==null){
            page=1;
        }
        if (pageSize==null){
         pageSize=PAGE_SIZE;
        }
        PagedGridResult pagedGridResult = itemService.queryItemComments(itemId, level, page, pageSize);
        return  JSONResult.ok(pagedGridResult);

    }


    @ApiOperation(value = "商品搜索列表",notes = "商品搜索列表",httpMethod = "GET")
    @GetMapping("/search")
    public JSONResult search(@ApiParam(name = "keywords",value = "关键字",required = true) @RequestParam String keywords,
                               @ApiParam(name = "sort",value = "排序") @RequestParam String sort,
                               @ApiParam(name = "page",value = "查询下一页的第几页") @RequestParam Integer page,
                               @ApiParam(name = "pageSize",value = "分页的每一页显示的条数") @RequestParam Integer pageSize){
        if (page==null){
            page=1;
        }
        if (pageSize==null){
            pageSize=PAGE_SIZE;
        }
        PagedGridResult pagedGridResult = itemService.searchItems(keywords,sort,page,pageSize);
        return  JSONResult.ok(pagedGridResult);

    }

    @ApiOperation(value = "通过分类ID搜索商品搜索列表",notes = "通过分类ID搜索商品搜索列表",httpMethod = "GET")
    @GetMapping("/catItems")
    public JSONResult catItems(@ApiParam(name = "catId",value = "分类ID",required = true) @RequestParam Integer catId,
                             @ApiParam(name = "sort",value = "排序") @RequestParam String sort,
                             @ApiParam(name = "page",value = "查询下一页的第几页") @RequestParam Integer page,
                             @ApiParam(name = "pageSize",value = "分页的每一页显示的条数") @RequestParam Integer pageSize){
        if (catId==null){
            return  JSONResult.errorMsg("分类ID不能为空");
        }
        if (page==null){
            page=1;
        }
        if (pageSize==null){
            pageSize=PAGE_SIZE;
        }
        PagedGridResult pagedGridResult = itemService.searchItemsByThirdCat(catId,sort,page,pageSize);
        return  JSONResult.ok(pagedGridResult);

    }
}
