package org.feather.food.service.center;

import org.feather.food.bo.center.CenterUserBO;
import org.feather.food.pojo.Users;

/**
 * @projectName: food
 * @package: org.feather.food.service.center
 * @className: CenterUserService
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022-10-29 7:26
 * @version: 1.0
 */

public interface CenterUserService {
    /**
     * 根据用户id查询用户信息
     * @param userId
     * @return
     */
    Users queryUserInfo(String userId);


    /**
     * 修改用户信息
     * @param userId
     * @param centerUserBO
     */
    Users  updateUserInfo(String userId, CenterUserBO centerUserBO);
}
