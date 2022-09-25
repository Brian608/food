package org.feather.food.service.impl;

import org.feather.food.mapper.ItemsImgMapper;
import org.feather.food.mapper.ItemsMapper;
import org.feather.food.mapper.ItemsParamMapper;
import org.feather.food.mapper.ItemsSpecMapper;
import org.feather.food.pojo.Items;
import org.feather.food.pojo.ItemsImg;
import org.feather.food.pojo.ItemsParam;
import org.feather.food.pojo.ItemsSpec;
import org.feather.food.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

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
}
