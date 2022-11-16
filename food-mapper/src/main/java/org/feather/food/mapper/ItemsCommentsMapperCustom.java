package org.feather.food.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.feather.food.my.mapper.MyMapper;
import org.feather.food.pojo.ItemsComments;
import org.feather.food.vo.MyCommentVO;

import java.util.List;
import java.util.Map;

@Mapper
public interface ItemsCommentsMapperCustom extends MyMapper<ItemsComments> {


    void saveComments(Map<String, Object> map);

     List<MyCommentVO> queryMyComments(@Param("paramsMap") Map<String, Object> map);
}