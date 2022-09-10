package org.feather.food.controller;

import org.apache.commons.lang3.StringUtils;
import org.feather.food.bo.UserBO;
import org.feather.food.common.utils.JSONResult;
import org.feather.food.pojo.Users;
import org.feather.food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/passport")
@RestController
public class PassPortController {

    @Autowired
    private  UserService userService;

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


    @PostMapping("/regist")
    public  JSONResult regist(@RequestBody UserBO userBO){
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
        return JSONResult.ok(user);

    }


}
