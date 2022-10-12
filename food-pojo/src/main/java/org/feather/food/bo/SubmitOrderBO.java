package org.feather.food.bo;

/**
 * @projectName: food
 * @package: org.feather.food.bo
 * @className: SubmitOrderBO
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022/10/9 22:10
 * @version: 1.0
 */
public class SubmitOrderBO {

    private String  userId;

    private String itemSpecIds;

    private String addressId;

    private Integer payMethod;

    private String leftMsg;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getItemSpecIds() {
        return itemSpecIds;
    }

    public void setItemSpecIds(String itemSpecIds) {
        this.itemSpecIds = itemSpecIds;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public String getLeftMsg() {
        return leftMsg;
    }

    public void setLeftMsg(String leftMsg) {
        this.leftMsg = leftMsg;
    }
}
