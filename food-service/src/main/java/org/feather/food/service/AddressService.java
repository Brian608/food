package org.feather.food.service;

import org.feather.food.bo.AddressBO;
import org.feather.food.pojo.UserAddress;

import java.util.List;

/**
 * @projectName: food
 * @package: org.feather.food.service
 * @className: AddressService
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/10/6 08:57
 * @version: 1.0
 */
public interface AddressService {
    /**
     * 根据用户ID查询用户收货地址列表
     * @param userId
     * @return
     */
    List<UserAddress> queryAll(String userId);

    /**
     * 用户新增地址
     * @param addressBO
     */
    void addNewUserAddress(AddressBO addressBO);

    /**
     * 修改用户地址
     * @param addressBO
     */
    void updateUserAddress(AddressBO addressBO);

    /**
     * 根据用户ID和地址ID，删除对应用户地址
     * @param userId
     * @param addressId
     */
    void deleteUserAddress(String userId,String addressId);

    /**
     * 修改用户默认地址
     * @param userId
     * @param addressId
     */
    void updateUserAddressToBeDefault(String userId,String addressId);
}
