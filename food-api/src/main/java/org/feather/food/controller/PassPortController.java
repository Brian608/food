package org.feather.food.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.feather.food.bo.ShopCartBO;
import org.feather.food.bo.UserBO;
import org.feather.food.common.utils.*;
import org.feather.food.pojo.Users;
import org.feather.food.service.UserService;
import org.feather.food.vo.UsersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

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
public class PassPortController  extends  BaseController{

    @Autowired
    private  UserService userService;

    @Autowired
    private RedisOperator redisOperator;

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
       // user=setNullProperty(user);
        // 实现用户的redis会话
        UsersVO usersVO = conventUsersVO(user);

        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(usersVO), true);
        // 同步购物车数据
        syncShopCartData(user.getId(),request,response);
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
       // users=setNullProperty(users);
        // 实现用户的redis会话
        UsersVO usersVO = conventUsersVO(users);

        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(usersVO), true);
        // 同步购物车数据
        syncShopCartData(users.getId(),request,response);
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
        // 清除用户的相关信息的cookie
        CookieUtils.deleteCookie(request, response, "user");

        // 用户退出登录，清除redis中user的会话信息
        redisOperator.del(REDIS_USER_TOKEN + ":" + userId);

        // 分布式会话中需要清除用户数据
        CookieUtils.deleteCookie(request, response, FOODIE_SHOPCART);
        return JSONResult.ok();

    }

    /**
     * 注册登录成功后，同步cookie 和redis中的购物车数据
     */
    public  void syncShopCartData(String userId,HttpServletRequest request,HttpServletResponse response){
        /**
         * 1.redis 中无数据  ，如果cookie中的数据为空，这个时候不做任何处理
         *                 如果cookie中的数据不为空，此时直接放入redis中
         * 2.redis有数据，  如果cookie中的购物车为空，那么直接把redis中的数据覆盖本地cookie
         *                 如果cookie中的数据不为空，如果cookie中的某个商品在redis中存在，则以cookie为主，删除redis中的数据，直接把cookie中的数据覆盖redis
         * 3.同步redis中去了以后，覆盖本地cookie中的购物车数据，保证本地购物车中的数据是同步最新的
         */

        //从redis中获取购物车
        String shopCartJsonRedis = redisOperator.get(FOODIE_SHOPCART + ":" + userId);

        //从cookie中获取购物车数据
        String shopCartJsonCookie = CookieUtils.getCookieValue(request, FOODIE_SHOPCART,true);

        if (StringUtils.isBlank(shopCartJsonRedis)){
            //redis为空，cookie不为空，直接把cookie中的数据放入redis
            if (StringUtils.isNotBlank(shopCartJsonCookie)){
                redisOperator.set(FOODIE_SHOPCART + ":" + userId,shopCartJsonCookie);
            }
        }else {
            //redis不为空 ，cookie不为空，合并cookie和redis中购物车中的商品数据(同一商品则覆盖redis)
            if (StringUtils.isNotBlank(shopCartJsonCookie)){
                /**
                 * 1. 已经存在的，把cookie中对应的数量，覆盖redis（参考京东）
                 * 2. 该项商品标记为待删除，统一放入一个待删除的list
                 * 3. 从cookie中清理所有的待删除list
                 * 4. 合并redis和cookie中的数据
                 * 5. 更新到redis和cookie中
                 */

                List<ShopCartBO> shopcartListRedis = JsonUtils.jsonToList(shopCartJsonRedis, ShopCartBO.class);
                List<ShopCartBO> shopcartListCookie = JsonUtils.jsonToList(shopCartJsonCookie, ShopCartBO.class);

                // 定义一个待删除list
                List<ShopCartBO> pendingDeleteList = new ArrayList<>();

                for (ShopCartBO redisShopcart : shopcartListRedis) {
                    String redisSpecId = redisShopcart.getSpecId();

                    for (ShopCartBO cookieShopcart : shopcartListCookie) {
                        String cookieSpecId = cookieShopcart.getSpecId();

                        if (redisSpecId.equals(cookieSpecId)) {
                            // 覆盖购买数量，不累加，参考京东
                            redisShopcart.setBuyCounts(cookieShopcart.getBuyCounts());
                            // 把cookieShopcart放入待删除列表，用于最后的删除与合并
                            pendingDeleteList.add(cookieShopcart);
                        }

                    }
                }

                // 从现有cookie中删除对应的覆盖过的商品数据
                shopcartListCookie.removeAll(pendingDeleteList);

                // 合并两个list
                shopcartListRedis.addAll(shopcartListCookie);
                // 更新到redis和cookie
                CookieUtils.setCookie(request, response, FOODIE_SHOPCART, JsonUtils.objectToJson(shopcartListRedis), true);

            }else {
                //redis 不为空，cookie为空  直接把redis覆盖cookie
                CookieUtils.setCookie(request,response,FOODIE_SHOPCART,shopCartJsonRedis,true);
            }
        }
    }



}
