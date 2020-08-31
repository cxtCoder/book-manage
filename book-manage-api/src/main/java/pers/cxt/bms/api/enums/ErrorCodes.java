package pers.cxt.bms.api.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author cxt
 * @Date 2020/6/25
 */
public enum ErrorCodes {

    SUCCESS("成功", "0"),

    USERNAME_IS_EMPTY("用户名不能为空！", "10000"),

    PASSWORD_IS_EMPTY("密码不能为空！", "10001"),

    REPASSWORD_IS_EMPTY("重新输入密码不能为空！", "10002"),

    PASSWORD_REPASSWORD_IS_NOT_SAME("两次密码输入不一致！", "10003"),

    USERNAME_ERROR("用户名不存在！", "10004"),

    PASSWORD_ERROR("密码输入错误！", "10005"),

    OR_PASSWORD_ERROR("原始密码输入错误！", "10006"),

    USERNAME_IS_EXIST("用户名已存在！", "10007"),

    USERNAME_IS_TOO_LONG("用户名长度过长！", "10008"),

    PASSWORD_IS_TOO_LONG("密码长度过长！", "10009"),

    PARAM_IS_NOT_COMPLETE("请求参数不完整", "10010"),

    ERROR_INNER_SYSTEM("系统异常", "90011"),

    INSERT_ERROR("新增失败", "90001"),

    DELETE_ERROR("删除失败", "90002"),

    UPDATE_ERROR("更新失败", "90003"),

    UPLOAD_ERROR("上传失败", "90004");

    private ErrorCodes(String errMsg, String code) {
        this.errMsg = errMsg;
        this.code = code;
    }

    private String errMsg;

    private String code;

    public String getErrMsg() {
        return errMsg;
    }

    public String getCode() {
        return code;
    }

    public Map<String, String> value() {
        Map<String, String> map = new HashMap<>();
        map.put("errMsg", getErrMsg());
        map.put("code", getCode());
        return map;
    }
}
