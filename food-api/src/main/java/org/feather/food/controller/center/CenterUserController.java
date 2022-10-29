package org.feather.food.controller.center;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.feather.food.bo.center.CenterUserBO;
import org.feather.food.common.utils.CookieUtils;
import org.feather.food.common.utils.JSONResult;
import org.feather.food.common.utils.JsonUtils;
import org.feather.food.pojo.Users;
import org.feather.food.service.center.CenterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @projectName: food
 * @package: org.feather.food.controller.center
 * @className: CenterUserController
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022-10-29 7:40
 * @version: 1.0
 */
@Api(value = "用户信息接口",tags = {"用户信息接口"})
@RequestMapping("/userInfo")
@RestController
public class CenterUserController {


    @Autowired
    private CenterUserService centerUserService;

    @ApiOperation(value = "修改用户信息",notes = "修改用户信息",httpMethod = "POST")
    @PostMapping("/update")
    public JSONResult update(
            @ApiParam(name = "userId",value = "用户id",required = true)@RequestParam String userId,
            @RequestBody  @Valid CenterUserBO centerUserBO,BindingResult bindingResult,
            HttpServletRequest request, HttpServletResponse response){
        // 判断BindingResult是否保存错误的验证信息，如果有，则直接return
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = getErrors(bindingResult);
            return JSONResult.errorMap(errorMap);
        }
        Users users = centerUserService.updateUserInfo(userId, centerUserBO);
        users=setNullProperty(users);
        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson(users),true);
        //TODO  后续要改，增加token令牌，会整合redis，分布式会话
        return JSONResult.ok(users);
    }

    private Map<String, String> getErrors(BindingResult result) {
        Map<String, String> map = new HashMap<>();
        List<FieldError> errorList = result.getFieldErrors();
        for (FieldError error : errorList) {
            // 发生验证错误所对应的某一个属性
            String errorField = error.getField();
            // 验证错误的信息
            String errorMsg = error.getDefaultMessage();

            map.put(errorField, errorMsg);
        }
        return map;
    }
    private Users setNullProperty(Users users){
        users.setPassword(null);
        users.setMobile(null);
        users.setEmail(null);
        users.setCreatedTime(null);
        users.setUpdatedTime(null);
        users.setBirthday(null);
        return users;
    }
}
