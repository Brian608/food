package org.feather.food.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.feather.food.vo.CategoryVO;
import org.feather.food.vo.NewItemsVO;

import java.util.List;
import java.util.Map;

@Mapper
public interface CategoryMapperCustom  {

    List<CategoryVO> getSubCatList (Integer rootCatId);

    List<NewItemsVO> getSixNewItemsLazy(@Param("paramsMap")Map<String, Object> map);
}