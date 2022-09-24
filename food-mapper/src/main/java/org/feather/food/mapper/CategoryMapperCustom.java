package org.feather.food.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.feather.food.vo.CategoryVO;

import java.util.List;

@Mapper
public interface CategoryMapperCustom  {

    List<CategoryVO> getSubCatList (Integer rootCatId);
}