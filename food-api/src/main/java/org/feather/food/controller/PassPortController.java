package org.feather.food.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.feather.food.bo.UserBO;
import org.feather.food.common.utils.CookieUtils;
import org.feather.food.common.utils.JSONResult;
import org.feather.food.common.utils.JsonUtils;
import org.feather.food.common.utils.MD5Utils;
import org.feather.food.pojo.Users;
import org.feather.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @projectName: food
 * @package: org.feather.food.controller
 * @className: PassPortController
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/9/10 18:13
 * @version: 1.0
 */
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(value = "注册登录",tags = {"注册登录接口"})
@RequestMapping("/passport")
@RestController
public class PassPortController {

    @Autowired
    private  UserService userService;

    @ApiOperation(value = "用户名是否存在",notes = "用户名是否存在",httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public JSONResult userNameIsExist(@RequestParam String username){
        if (StringUtils.isBlank(username)){
            return JSONResult.errorMsg("用户名为控");
        }
        boolean isExist = userService.queryUserNameIsExist(username);
        if (isExist){
            return  JSONResult.errorMsg("用户名存在");
        }
        return JSONResult.ok();
    }

    @ApiOperation(value = "用户注册",notes = "用户注册",httpMethod = "POST")
    @PostMapping("/regist")
    public  JSONResult regist(@RequestBody UserBO userBO,HttpServletRequest request , HttpServletResponse response){
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPassWord = userBO.getConfirmPassWord();
        if (StringUtils.isBlank(username)||StringUtils.isBlank(password)||StringUtils.isBlank(confirmPassWord)){
            return JSONResult.errorMsg("用户名或密码不能为空");
        }
        boolean isExist = userService.queryUserNameIsExist(username);
        if (isExist){
            return  JSONResult.errorMsg("用户名存在");
        }
        if (password.length()<6){
            return  JSONResult.errorMsg(" 密码长度小于6");
        }
        if (!password.equals(confirmPassWord)){
            return  JSONResult.errorMsg("两次密码不一致");
        }
        Users user = userService.createUser(userBO);
        user=setNullProperty(user);
        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson(user),true);
        return JSONResult.ok(user);

    }
    @ApiOperation(value = "用户登录",notes = "用户登录",httpMethod = "POST")
    @PostMapping("/login")
    public  JSONResult login(@RequestBody UserBO userBO, HttpServletRequest request , HttpServletResponse response) throws Exception {
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        if (StringUtils.isBlank(username)||StringUtils.isBlank(password)){
            return JSONResult.errorMsg("用户名或密码不能为空");
        }
        Users users = userService.queryForLogin(username, MD5Utils.getMD5Str(password));
        if (users==null){
            return JSONResult.errorMsg("用户名或密码不正确");
        }
        users=setNullProperty(users);
        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson(users),true);
        return JSONResult.ok(users);

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

    @ApiOperation(value = "退出登录",notes = "退出登录",httpMethod = "POST")
    @PostMapping("/logout")
    public  JSONResult logout(@RequestParam String userId, HttpServletRequest request , HttpServletResponse response){
      CookieUtils.deleteCookie(request ,response,"user");
      //todo 用户退出登录，需要清楚购物车
        //todo 分布式会话中需要清除用户数据
        return JSONResult.ok();

    }



}
