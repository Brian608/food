package org.feather.food.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.feather.food.my.mapper.MyMapper;
import org.feather.food.pojo.Carousel;
@Mapper
public interface CarouselMapper extends MyMapper<Carousel> {
}