package org.feather.food.service.impl;

import org.feather.food.bo.AddressBO;
import org.feather.food.common.enums.YesOrNoEnum;
import org.feather.food.mapper.UserAddressMapper;
import org.feather.food.pojo.UserAddress;
import org.feather.food.service.AddressService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @projectName: food
 * @package: org.feather.food.service.impl
 * @className: AddressServiceImpl
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/10/6 08:58
 * @version: 1.0
 */
@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private UserAddressMapper userAddressMapper;

    @Autowired
    private Sid sid;



    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<UserAddress> queryAll(String userId) {
        UserAddress userAddress=new UserAddress();
        userAddress.setUserId(userId);
        return userAddressMapper.select(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public void addNewUserAddress(AddressBO addressBO) {
        Integer isDefault=0;
        //1.判断当前用户是否存在地址，如果不存在则新增为默认地址
        List<UserAddress> userAddresses = this.queryAll(addressBO.getUserId());
        if (userAddresses == null || userAddresses.isEmpty()){
        isDefault=1;
        }
        String  addressId= sid.nextShort();
        UserAddress userAddress=new UserAddress();
        BeanUtils.copyProperties(addressBO,userAddress);
        userAddress.setId(addressId);
        userAddress.setIsDefault(isDefault);
        userAddress.setCreatedTime(new Date());
        userAddress.setUpdatedTime(new Date());
        userAddressMapper.insert(userAddress);

    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public void updateUserAddress(AddressBO addressBO) {
        String addressId = addressBO.getAddressId();
        UserAddress userAddress=new UserAddress();
        BeanUtils.copyProperties(addressBO,userAddress);
        userAddress.setId(addressId);
        userAddress.setUpdatedTime(new Date());
        userAddressMapper.updateByPrimaryKeySelective(userAddress);

    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public void deleteUserAddress(String userId, String addressId) {
        UserAddress userAddress=new UserAddress();
        userAddress.setUserId(userId);
        userAddress.setId(addressId);
        userAddressMapper.deleteByExample(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public void updateUserAddressToBeDefault(String userId, String addressId) {
        //查找默认地址，设置为不是默认的
        UserAddress userAddress=new UserAddress();
        userAddress.setIsDefault(YesOrNoEnum.YES.getType());
        userAddress.setUserId(userId);
        List<UserAddress> list = userAddressMapper.select(userAddress);
        for (UserAddress ua: list ) {
            ua.setIsDefault(YesOrNoEnum.NO.getType());
            userAddressMapper.updateByPrimaryKeySelective(ua);
        }
        //根据地址ID修改为默认地址
        UserAddress address=new UserAddress();
        address.setId(addressId);
        address.setUserId(userId);
        address.setIsDefault(YesOrNoEnum.YES.getType());
        userAddressMapper.updateByPrimaryKeySelective(address);
    }
}
