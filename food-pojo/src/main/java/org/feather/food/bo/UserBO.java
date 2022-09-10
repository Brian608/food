package org.feather.food.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @projectName: food
 * @package: org.feather.food.bo
 * @className: UserBO
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/9/10 18:53
 * @version: 1.0
 */
@ApiModel(value = "用户对象BO",description = "从客户端，由用户传入的数据封装的BO")
public class UserBO {

  @ApiModelProperty(value = "用户名",name ="username",example = "zhangsan",required = true)
    private String username;

    @ApiModelProperty(value = "密码",name ="password",example = "123456",required = true)
    private String password;

    @ApiModelProperty(value = "确认密码",name ="confirmPassWord",example = "123456",required = true)
    private String confirmPassWord;

    public String getUsername() {
        return username;
    }

    public String getConfirmPassWord() {
        return confirmPassWord;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassWord(String confirmPassWord) {
        this.confirmPassWord = confirmPassWord;
    }
}
