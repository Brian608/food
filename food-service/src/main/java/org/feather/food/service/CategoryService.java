package org.feather.food.service;

import org.feather.food.pojo.Category;
import org.feather.food.vo.CategoryVO;
import org.feather.food.vo.NewItemsVO;

import java.util.List;

/**
 * @projectName: food
 * @package: org.feather.food.service
 * @className: CategoryService
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/9/24 22:00
 * @version: 1.0
 */
public interface CategoryService {
    /**
     * 查询所有一级分类
     * @return
     */
   List<Category> queryAllRootLevelCat();

    /**
     *  根据父ID查询子分类
     * @param rootCatId
     * @return
     */
   List<CategoryVO> getSubCatList(Integer rootCatId);

    /**
     * 查询首页每个一级分类下的六条最新商品数据
     * @param rootCatId
     * @return
     */

   List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId);


}
