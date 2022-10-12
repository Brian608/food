package org.feather.food.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.feather.food.my.mapper.MyMapper;
import org.feather.food.pojo.Items;
import org.feather.food.vo.ItemCommentVO;
import org.feather.food.vo.SearchItemsVO;
import org.feather.food.vo.ShopCartVO;

import java.util.List;
import java.util.Map;

@Mapper
public interface ItemsMapperCustom  {


    List<ItemCommentVO> queryItemComments(@Param("paramsMap")Map<String, Object> map);

    List<SearchItemsVO> searchItems(@Param("paramsMap")Map<String, Object> map);


    List<SearchItemsVO> searchItemsByThirdCat(@Param("paramsMap")Map<String, Object> map);

    List<ShopCartVO>  queryItemsBySpecIds (@Param("paramsList")List specIdsList);



    int decreaseItemSpecStock(@Param("specId")String specId,@Param("pendingCounts") int pendingCounts);

}