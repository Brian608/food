package org.feather.food.controller.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.feather.food.common.utils.JSONResult;
import org.feather.food.common.utils.JsonUtils;
import org.feather.food.common.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @projectName: food
 * @package: org.feather.food.controller.interceptor
 * @className: UserTokenInterceptor
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2023-02-01 17:13
 * @version: 1.0
 */

public class UserTokenInterceptor  implements HandlerInterceptor {

    @Autowired
    private RedisOperator redisOperator;

    public static  final String  REDIS_USER_TOKEN="redis_user_token";


    /**
     * 拦截请求 在访问controller调用方法之前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      // log.info("进入到拦截器被拦截......");
        String userId = request.getHeader("headerUserId");
        String userToken=request.getHeader("headerUserToken");
        if (StringUtils.isNotBlank(userId)&&StringUtils.isNotBlank(userToken)){
            String uniqueToken=redisOperator.get(REDIS_USER_TOKEN+":"+userId);
            if (StringUtils.isBlank(uniqueToken)){
                returnErrorResponse(response,new JSONResult("请登录......"));
                return  false;
            }else {
                if (!uniqueToken.equals(userToken)){
                  //  log.info("账户在异地登录......");
                    returnErrorResponse(response,new JSONResult("账户在异地登录......"));
                    return  false;
                }
            }
        }else {
          //  log.info("请登录......");
            returnErrorResponse(response,new JSONResult("请登录......"));
            return  false;
        }
        /**
         * false 请求被拦截，被驳回，验证出现问题
         * true: 请求在经过验证校验以后都是ok 的。只可以放行的
         */
        return true;
    }

    /**
     * 请求访问controller之后，渲染视图之前
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }


    /**
     * 请求访问controller之后，渲染视图之后
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    public void returnErrorResponse(HttpServletResponse response,
                                    JSONResult result) {
        OutputStream out = null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");
            out = response.getOutputStream();
            out.write(Objects.requireNonNull(JsonUtils.objectToJson(result)).getBytes(StandardCharsets.UTF_8));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
