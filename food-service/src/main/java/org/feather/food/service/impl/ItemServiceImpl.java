package org.feather.food.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.feather.food.common.enums.CommentLevelEnum;
import org.feather.food.common.utils.DesensitizationUtil;
import org.feather.food.common.utils.PagedGridResult;
import org.feather.food.mapper.*;
import org.feather.food.pojo.*;
import org.feather.food.service.ItemService;
import org.feather.food.vo.CommentLevelCountsVO;
import org.feather.food.vo.ItemCommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @projectName: food
 * @package: org.feather.food.service.impl
 * @className: ItemServiceImpl
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/9/25 20:54
 * @version: 1.0
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemsMapper itemsMapper;

    @Autowired
    private ItemsImgMapper itemsImgMapper;

    @Autowired
    private ItemsSpecMapper itemsSpecMapper;

    @Autowired
    private ItemsParamMapper itemsParamMapper;

    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;

    @Autowired
    private ItemsMapperCustom itemsMapperCustom;


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Items queryItemById(String id) {
        return itemsMapper.selectByPrimaryKey(id);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsImg> queryItemImgList(String itemId) {
        Example example=new Example(ItemsImg.class);
        Example.Criteria criteria= example.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        return itemsImgMapper.selectByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        Example example=new Example(ItemsImg.class);
        Example.Criteria criteria= example.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        return itemsSpecMapper.selectByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsParam queryItemParam(String itemId) {
        Example example=new Example(ItemsImg.class);
        Example.Criteria criteria= example.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        return itemsParamMapper.selectOneByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public CommentLevelCountsVO queryCommentCounts(String itemId) {
        Integer goodCounts=getComentCounts(itemId, CommentLevelEnum.GOOD.getType());
        Integer normalCounts=getComentCounts(itemId, CommentLevelEnum.GOOD.getType());
        Integer badCounts=getComentCounts(itemId, CommentLevelEnum.GOOD.getType());
        Integer totalCounts=goodCounts+normalCounts+badCounts;
        CommentLevelCountsVO commentLevelCountsVO=new CommentLevelCountsVO();
        commentLevelCountsVO.setGoodCounts(goodCounts);
        commentLevelCountsVO.setNormalCounts(normalCounts);
        commentLevelCountsVO.setBadCounts(badCounts);
        commentLevelCountsVO.setTotalCounts(totalCounts);
        return commentLevelCountsVO;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    Integer getComentCounts(String itemId,Integer level){
        ItemsComments condition=new ItemsComments();
        condition.setItemId(itemId);
        if (level!=null){
            condition.setCommentLevel(level);
        }
      return   itemsCommentsMapper.selectCount(condition);
    }



    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult  queryItemComments(String itemId, Integer level,Integer page,Integer pageSize) {
        Map<String, Object> map=new HashMap<>();
        map.put("itemId",itemId);
        map.put("level",level);
        PageHelper.startPage(page,pageSize);
        List<ItemCommentVO> itemCommentVOList = itemsMapperCustom.queryItemComments(map);
        for (ItemCommentVO itemCommentVO:
            itemCommentVOList ) {
            itemCommentVO.setNickname(DesensitizationUtil.commonDisplay(itemCommentVO.getNickname()));
        }
        return  setterPagedGrid(itemCommentVOList,page);
    }

    private PagedGridResult setterPagedGrid(List<?> list,Integer page){
        PageInfo<?> pageList=new PageInfo<>(list);
        PagedGridResult pagedGridResult=new PagedGridResult();
        pagedGridResult.setPage(page);
        pagedGridResult.setRows(list);
        pagedGridResult.setTotal(pageList.getPages());
        pagedGridResult.setRecords(pageList.getTotal());
        return  pagedGridResult;
    }
}
