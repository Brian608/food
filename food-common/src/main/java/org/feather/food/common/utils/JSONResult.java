package org.feather.food.common.utils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * @projectName: food
 * @package: org.feather.food.common
 * @className: JSONResult
 * @author: feather(杜雪松)
 * @description:
 *  *               自定义响应数据结构
 *  * 				本类可提供给 H5/ios/安卓/公众号/小程序 使用
 *  * 				前端接受此类数据（json object)后，可自行根据业务去实现相关功能
 *  *
 *  * 				200：表示成功
 *  * 				500：表示错误，错误信息在msg字段中
 *  * 				501：bean验证错误，不管多少个错误都以map形式返回
 *  * 				502：拦截器拦截到用户token出错
 *  * 				555：异常抛出信息
 *  * 				556: 用户qq校验异常
 *                  557 校验用户是否在CAS登录，用户门票的检验
 * @since: 2022/9/10 18:28
 * @version: 1.0
 */
public class JSONResult {
    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // 响应业务状态
    private Integer code;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    @JsonIgnore
    private String ok;	// 不使用

    public static JSONResult build(Integer code, String msg, Object data) {
        return new JSONResult(code, msg, data);
    }

    public static JSONResult build(Integer code, String msg, Object data, String ok) {
        return new JSONResult(code, msg, data, ok);
    }

    public static JSONResult ok(Object data) {
        return new JSONResult(data);
    }

    public static JSONResult ok() {
        return new JSONResult(null);
    }

    public static JSONResult errorMsg(String msg) {
        return new JSONResult(500, msg, null);
    }

    public static JSONResult errorMap(Object data) {
        return new JSONResult(501, "error", data);
    }

    public static JSONResult errorTokenMsg(String msg) {
        return new JSONResult(502, msg, null);
    }

    public static JSONResult errorException(String msg) {
        return new JSONResult(555, msg, null);
    }

    public static JSONResult errorUserQQ(String msg) {
        return new JSONResult(556, msg, null);
    }

    public static JSONResult errorUserTicket(String msg) {
        return new JSONResult(556, msg, null);
    }

    public JSONResult() {

    }

    public JSONResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public JSONResult(Integer code, String msg, Object data, String ok) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.ok = ok;
    }

    public JSONResult(Object data) {
        this.code = 200;
        this.msg = "OK";
        this.data = data;
    }

    public Boolean isOK() {
        return this.code == 200;
    }

    public Integer getcode() {
        return code;
    }

    public void setcode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

}
