package org.feather.food.service.impl;

import org.feather.food.mapper.CarouselMapper;
import org.feather.food.pojo.Carousel;
import org.feather.food.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @projectName: food
 * @package: org.feather.food.service.impl
 * @className: CarouselServiceImpl
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/9/24 21:21
 * @version: 1.0
 */
@Service
public class CarouselServiceImpl  implements CarouselService {
    @Autowired
    private CarouselMapper carouselMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Carousel> queryAll(Integer isShow) {
        Example example=new Example(Carousel.class);
        example.orderBy("sort").desc();
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("isShow",isShow);
        return carouselMapper.selectByExample(example);
    }
}
