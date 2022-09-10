package org.feather.food.service;

import org.feather.food.bo.UserBO;
import org.feather.food.pojo.Users;

/**
 * @projectName: food
 * @package: org.feather.food.service
 * @className: UserService
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/9/10 18:03
 * @version: 1.0
 */
public interface UserService {
    /**
     * 判断用户名是否存在
     * @param username
     * @return
     */
    boolean queryUserNameIsExist(String username);


    Users createUser(UserBO userBO);
}
