package org.feather.food.service;

import org.feather.food.common.utils.PagedGridResult;
import org.feather.food.pojo.Items;
import org.feather.food.pojo.ItemsImg;
import org.feather.food.pojo.ItemsParam;
import org.feather.food.pojo.ItemsSpec;
import org.feather.food.vo.CommentLevelCountsVO;
import org.feather.food.vo.ItemCommentVO;

import java.util.List;

/**
 * @projectName: food
 * @package: org.feather.food.service
 * @className: ItemService
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/9/25 20:50
 * @version: 1.0
 */
public interface ItemService {
    /**
     * 根据商品ID查询详情
     * @param id
     * @return
     */
    Items queryItemById(String id);

    /**
     * 根据商品ID查询商品列表
     * @param itemId
     * @return
     */
    List<ItemsImg> queryItemImgList(String itemId);

    /**
     *根据商品ID查询商品规则
     * @param itemId
     * @return
     */
    List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 根据商品ID查询商品属性
     * @param itemId
     * @return
     */
    ItemsParam queryItemParam(String itemId);

    /**
     * 查询商品评价等级
     * @param itemId
     * @return
     */
     CommentLevelCountsVO  queryCommentCounts(String itemId);


    /**
     * 根据商品ID查询商品的评价
     * @param itemId
     * @param level
     * @return
     */
    PagedGridResult queryItemComments(String itemId, Integer level, Integer page, Integer pageSize);
}
