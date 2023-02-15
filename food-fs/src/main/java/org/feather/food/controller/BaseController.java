package org.feather.food.controller;

import org.feather.food.common.utils.RedisOperator;
import org.feather.food.pojo.Users;
import org.feather.food.vo.UsersVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.UUID;

/**
 * @projectName: food
 * @package: org.feather.food.controller
 * @className: BaseController
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2023-02-15 16:39
 * @version: 1.0
 */

@Controller
public class BaseController {

    @Autowired
    private RedisOperator redisOperator;

    public static final String REDIS_USER_TOKEN = "redis_user_token";


    public UsersVO conventUsersVO(Users user) {
        // 实现用户的redis会话
        String uniqueToken = UUID.randomUUID().toString().trim();
        redisOperator.set(REDIS_USER_TOKEN + ":" + user.getId(),
                uniqueToken);

        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(user, usersVO);
        usersVO.setUserUniqueToken(uniqueToken);
        return usersVO;
    }
}
