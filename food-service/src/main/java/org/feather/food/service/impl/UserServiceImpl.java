package org.feather.food.service.impl;

import org.feather.food.bo.UserBO;
import org.feather.food.common.enums.SexEnum;
import org.feather.food.common.utils.DateUtil;
import org.feather.food.common.utils.MD5Utils;
import org.feather.food.mapper.UsersMapper;
import org.feather.food.pojo.Users;
import org.feather.food.service.UserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * @projectName: food
 * @package: org.feather.food.service.impl
 * @className: UserServiceImpl
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/9/10 18:05
 * @version: 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private Sid sid;


    private static final String USER_FACE = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUserNameIsExist(String username) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",username);
        Users users = usersMapper.selectOneByExample(example);
        return users != null;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users  createUser(UserBO userBO) {
        String userId = sid.nextShort();
        Users users=new Users();
        users.setId(userId);
        users.setUsername(userBO.getUsername());
        try {
            users.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        users.setUsername(userBO.getUsername());
        users.setFace(USER_FACE);
        users.setBirthday(DateUtil.stringToDate("1996-01-01"));
        users.setSex(SexEnum.SECRET.getType());
        users.setCreatedTime(new Date());
        users.setUpdatedTime(new Date());
        usersMapper.insert(users);
        return users;
    }
}
