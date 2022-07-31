package org.feather.food.service.impl;

import org.feather.food.mapper.StuMapper;
import org.feather.food.pojo.Stu;
import org.feather.food.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @projectName: food
 * @package: org.feather.food.service.impl
 * @className: StuServiceImpl
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/7/31 20:25
 * @version: 1.0
 */
@Service
public class StuServiceImpl implements StuService {

    @Autowired
    private StuMapper stuMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Stu getStuInfo(int id) {
        return stuMapper.selectByPrimaryKey(id);
    }

    @Override
    public void save(Stu stu) {

    }

    @Override
    public void update(Stu stu) {

    }

    @Override
    public void delete(int id) {

    }
}
