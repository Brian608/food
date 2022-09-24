package org.feather.food.service;

import org.feather.food.pojo.Carousel;

import java.util.List;

/**
 * @projectName: food
 * @package: org.feather.food.service
 * @className: CarouselService
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/9/24 21:20
 * @version: 1.0
 */
public interface CarouselService {
    /**
     * 查询所有轮播图
     * @param isShow
     * @return
     */
    List<Carousel> queryAll(Integer isShow);

}
