package org.feather.food.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.feather.food.common.enums.YesOrNoEnum;
import org.feather.food.common.utils.JSONResult;
import org.feather.food.common.utils.JsonUtils;
import org.feather.food.common.utils.RedisOperator;
import org.feather.food.pojo.Carousel;
import org.feather.food.pojo.Category;
import org.feather.food.service.CarouselService;
import org.feather.food.service.CategoryService;
import org.feather.food.vo.CategoryVO;
import org.feather.food.vo.NewItemsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @projectName: food
 * @package: org.feather.food.controller
 * @className: IndexController
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/9/24 21:24
 * @version: 1.0
 */
@Api(value = "首页",tags = { "首页展示的相关接口"})
@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private CarouselService carouselService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisOperator redisOperator;

    @ApiOperation(value = "获取首页轮播图列表",notes = "获取首页轮播图列表",httpMethod = "GET")
    @GetMapping("/carousel")
    public JSONResult carousel(){
        String carouselStr = redisOperator.get("carousel");
        List<Carousel> carousels;
        if (StringUtils.isBlank(carouselStr)){
            carousels   = carouselService.queryAll(YesOrNoEnum.YES.getType());
            redisOperator.set("carousel", JsonUtils.objectToJson(carousels));
        }else {
            carousels=JsonUtils.jsonToList(carouselStr,Carousel.class);
        }

        return JSONResult.ok(carousels);
    }
    /**
     * 1.后台运营系统，一旦广告（轮播图）发生更改，就可以删除缓存，然后重置
     * 2.定时重置，比如每天凌晨三点
     * 3.每个轮播图都有可能是一个广告，每个广告都会有一个过期时间，过期了，再重置
     */

    /**
     * 首页分类展示需求
     * 1。第一次刷新主页查询大分类，渲染展示到首页
     * 2。如果鼠标上移到大分类，则加载子分类的内容，如果已经存在子分类，则不需要（懒加载）
     */
    @ApiOperation(value = "获取商品分类（一级）",notes = "获取商品分类（一级）",httpMethod = "GET")
    @GetMapping("/cats")
    public JSONResult cats(){
        List<Category> categoryList;
        String catsStr = redisOperator.get("cats");
        if (StringUtils.isBlank(catsStr)){
            categoryList=categoryService.queryAllRootLevelCat();
            redisOperator.set("cats",JsonUtils.objectToJson(categoryList));
        }else {
            categoryList=JsonUtils.jsonToList(catsStr,Category.class);
        }

        return JSONResult.ok(categoryList);
    }

    @ApiOperation(value = "获取商品子分类",notes = "获取商品子分类",httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public JSONResult subCat(
            @ApiParam(name = "rootCatId",value = "一级分类id",required = true)
            @PathVariable Integer  rootCatId){
        if (rootCatId==null){
            return  JSONResult.errorMsg("分类不存在");
        }
        List<CategoryVO> subCatList;
        String catsStr = redisOperator.get("subCat:" + rootCatId);
        if (StringUtils.isBlank(catsStr)){
            subCatList = categoryService.getSubCatList(rootCatId);
            redisOperator.set("subCat:" + rootCatId,JsonUtils.objectToJson(subCatList));
        }else {
            subCatList=JsonUtils.jsonToList(catsStr,CategoryVO.class);
        }
        return JSONResult.ok(subCatList);
    }

    @ApiOperation(value = "查询每个一级分类下的最新六条分类数据",notes = "查询每个一级分类下的最新六条分类数据",httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public JSONResult sixNewItems(
            @ApiParam(name = "rootCatId",value = "一级分类id",required = true)
            @PathVariable Integer  rootCatId){
        if (rootCatId==null){
            return  JSONResult.errorMsg("分类不存在");
        }
        List<NewItemsVO> sixNewItemsLazy = categoryService.getSixNewItemsLazy(rootCatId);
        return JSONResult.ok(sixNewItemsLazy);
    }


}
