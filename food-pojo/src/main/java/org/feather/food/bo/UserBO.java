package org.feather.food.bo;

/**
 * @projectName: food
 * @package: org.feather.food.bo
 * @className: UserBO
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/9/10 18:53
 * @version: 1.0
 */
public class UserBO {

    private String username;

    private String password;

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
