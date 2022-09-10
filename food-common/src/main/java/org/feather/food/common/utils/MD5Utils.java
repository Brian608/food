package org.feather.food.common.utils;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;

/**
 * @projectName: food
 * @package: org.feather.food.common.utils
 * @className: MD5Utils
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/9/10 18:57
 * @version: 1.0
 */
public class MD5Utils {

    /**
     *
     * @Title: MD5Utils.java
     * @Package com.imooc.utils
     * @Description: 对字符串进行md5加密
     */
    public static String getMD5Str(String strValue) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        return Base64.encodeBase64String(md5.digest(strValue.getBytes()));
    }


}
