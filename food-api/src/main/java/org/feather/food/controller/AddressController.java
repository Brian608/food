package org.feather.food.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.feather.food.bo.AddressBO;
import org.feather.food.common.utils.JSONResult;
import org.feather.food.common.utils.MobileEmailUtils;
import org.feather.food.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @projectName: food
 * @package: org.feather.food.controller
 * @className: AddressController
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/10/6 08:51
 * @version: 1.0
 */
@Api(value = "地址相关", tags = {"地址相关的api接口"})
@RequestMapping("/address")
@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    /**
     * 用户在确认订单页面可以针对收获地址做如下操作
     * 1：查询用户所有的收获地址列表
     * 2：新增收获地址
     * 3：删除收获地址
     * 4：修改收货地址
     * 5：设置默认收货地址
     */


    @ApiOperation(value = "根据用户ID查询收货地址列表", notes = "根据用户ID查询收货地址列表", httpMethod = "POST")
    @PostMapping("/list")
    public JSONResult list(@RequestParam String userId) {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("");
        }
        return JSONResult.ok(addressService.queryAll(userId));

    }

    @ApiOperation(value = "用户新增地址", notes = "用户新增地址", httpMethod = "POST")
    @PostMapping("/add")
    public JSONResult add(@RequestBody AddressBO addressBO) {
        JSONResult jsonResult = checkAddress(addressBO);
        if (jsonResult.getcode() != 200) {
            return jsonResult;
        }
        addressService.addNewUserAddress(addressBO);
        return JSONResult.ok();

    }

    @ApiOperation(value = "用户修改地址", notes = "用户修改地址", httpMethod = "POST")
    @PostMapping("/update")
    public JSONResult update(@RequestBody AddressBO addressBO) {
        if (StringUtils.isBlank(addressBO.getAddressId())) {
            return JSONResult.errorMsg("用户地址ID为空");
        }
        JSONResult jsonResult = checkAddress(addressBO);
        if (jsonResult.getcode() != 200) {
            return jsonResult;
        }
        addressService.updateUserAddress(addressBO);
        return JSONResult.ok();

    }

    @ApiOperation(value = "用户删除地址", notes = "用户删除地址", httpMethod = "POST")
    @PostMapping("/delete")
    public JSONResult delete(@RequestParam String userId, @RequestParam String addressId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return JSONResult.errorMsg("");
        }
        addressService.deleteUserAddress(userId, addressId);
        return JSONResult.ok();

    }



    @ApiOperation(value = "用户设置默认地址", notes = "用户设置默认地址", httpMethod = "POST")
    @PostMapping("/setDefault")
    public JSONResult setDefault(@RequestParam String userId, @RequestParam String addressId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return JSONResult.errorMsg("");
        }
        addressService.updateUserAddressToBeDefault(userId, addressId);
        return JSONResult.ok();

    }

    private JSONResult checkAddress(AddressBO addressBO) {
        String receiver = addressBO.getReceiver();
        if (StringUtils.isBlank(receiver)) {
            return JSONResult.errorMsg("收货人不能为空");
        }
        if (receiver.length() > 12) {
            return JSONResult.errorMsg("收货人姓名不能太长");
        }

        String mobile = addressBO.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return JSONResult.errorMsg("收货人手机号不能为空");
        }
        if (mobile.length() != 11) {
            return JSONResult.errorMsg("收货人手机号长度不正确");
        }
        boolean isMobileOk = MobileEmailUtils.checkMobileIsOk(mobile);
        if (!isMobileOk) {
            return JSONResult.errorMsg("收货人手机号格式不正确");
        }

        String province = addressBO.getProvince();
        String city = addressBO.getCity();
        String district = addressBO.getDistrict();
        String detail = addressBO.getDetail();
        if (StringUtils.isBlank(province) ||
                StringUtils.isBlank(city) ||
                StringUtils.isBlank(district) ||
                StringUtils.isBlank(detail)) {
            return JSONResult.errorMsg("收货地址信息不能为空");
        }

        return JSONResult.ok();
    }


}
