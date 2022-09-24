package org.feather.food.service.impl;

import com.sun.scenario.effect.impl.prism.PrImage;
import org.feather.food.mapper.CategoryMapper;
import org.feather.food.mapper.CategoryMapperCustom;
import org.feather.food.pojo.Category;
import org.feather.food.service.CategoryService;
import org.feather.food.vo.CategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @projectName: food
 * @package: org.feather.food.service.impl
 * @className: CategoryServiceImpl
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/9/24 22:02
 * @version: 1.0
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryMapperCustom categoryMapperCustom;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryAllRootLevelCat() {
        Example example=new Example(Category.class);
        Example.Criteria criteria= example.createCriteria();
        criteria.andEqualTo("type",1);
        return categoryMapper.selectByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<CategoryVO> getSubCatList(Integer rootCatId) {
        return categoryMapperCustom.getSubCatList(rootCatId);
    }
}
