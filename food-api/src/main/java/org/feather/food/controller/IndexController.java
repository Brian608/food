package org.feather.food.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.feather.food.common.enums.YesOrNoEnum;
import org.feather.food.common.utils.JSONResult;
import org.feather.food.pojo.Carousel;
import org.feather.food.pojo.Category;
import org.feather.food.service.CarouselService;
import org.feather.food.service.CategoryService;
import org.feather.food.vo.CategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation(value = "获取首页轮播图列表",notes = "获取首页轮播图列表",httpMethod = "GET")
    @GetMapping("/carousel")
    public JSONResult carousel(){
        List<Carousel> carousels = carouselService.queryAll(YesOrNoEnum.YES.getType());
        return JSONResult.ok(carousels);
    }
    /**
     * 首页分类展示需求
     * 1。第一次刷新主页查询大分类，渲染展示到首页
     * 2。如果鼠标上移到大分类，则加载子分类的内容，如果已经存在子分类，则不需要（懒加载）
     */
    @ApiOperation(value = "获取商品分类（一级）",notes = "获取商品分类（一级）",httpMethod = "GET")
    @GetMapping("/cats")
    public JSONResult cats(){
        List<Category> categoryList = categoryService.queryAllRootLevelCat();
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
        List<CategoryVO> subCatList = categoryService.getSubCatList(rootCatId);
        return JSONResult.ok(subCatList);
    }


}
