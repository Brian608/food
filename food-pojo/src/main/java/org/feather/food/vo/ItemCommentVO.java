package org.feather.food.vo;

import java.util.Date;

/**
 * @projectName: food
 * @package: org.feather.food.vo
 * @className: ItemCommentVO
 * @author: feather(杜雪松)
 * @description: 用于展示商品评价的VO
 * @since: 2022/9/26 08:18
 * @version: 1.0
 */
public class ItemCommentVO {

    private Integer commentLevel;

    private String content;

    private String sepcName;


    private Date createdTime;

    private String userFace;

    private String nickname;

    public Integer getCommentLevel() {
        return commentLevel;
    }

    public void setCommentLevel(Integer commentLevel) {
        this.commentLevel = commentLevel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSepcName() {
        return sepcName;
    }

    public void setSepcName(String sepcName) {
        this.sepcName = sepcName;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getUserFace() {
        return userFace;
    }

    public void setUserFace(String userFace) {
        this.userFace = userFace;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
