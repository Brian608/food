package org.feather.food.service.impl.center;

import org.feather.food.bo.center.CenterUserBO;
import org.feather.food.mapper.UsersMapper;
import org.feather.food.pojo.Users;
import org.feather.food.service.center.CenterUserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.Date;

/**
 * @projectName: food
 * @package: org.feather.food.service.impl.center
 * @className: CenterUserServiceImpl
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022-10-29 7:28
 * @version: 1.0
 */

@Service
public class CenterUserServiceImpl implements CenterUserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private Sid sid;


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserInfo(String userId) {
        Users users = usersMapper.selectByPrimaryKey(userId);
        users.setPassword(null);
        return users;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users updateUserInfo(String userId, CenterUserBO centerUserBO) {
        Users updateUsers=new Users();
        updateUsers.setId(userId);
        updateUsers.setUpdatedTime(new Date());
        BeanUtils.copyProperties(centerUserBO,updateUsers);
        usersMapper.updateByPrimaryKey(updateUsers);
        return queryUserInfo(userId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users updateUserFace(String userId, String faceUrl) {
        Users updateUsers=new Users();
        updateUsers.setId(userId);
        updateUsers.setUpdatedTime(new Date());
        updateUsers.setFace(faceUrl);
        usersMapper.updateByPrimaryKey(updateUsers);
        return queryUserInfo(userId);
    }
}
