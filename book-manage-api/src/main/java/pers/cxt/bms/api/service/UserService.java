package pers.cxt.bms.api.service;

import com.hundsun.jrescloud.rpc.annotation.CloudFunction;
import com.hundsun.jrescloud.rpc.annotation.CloudService;
import pers.cxt.bms.api.dto.UserDTO;
import pers.cxt.bms.api.entity.ResponseEntity;

/**
 * Created by chaoxt20781 on 2018/9/29.
 */
@CloudService
public interface UserService {
    /**
     * 用户登录
     *
     * @param userName 用户名
     * @param password 密码
     * @return
     */
    @CloudFunction("checkLogin")
    ResponseEntity checkLogin(String userName, String password);

    /**
     * 用户注册
     *
     * @param userName   用户名
     * @param password   密码
     * @param rePassword 重复密码
     * @return
     */
    @CloudFunction("registerUser")
    ResponseEntity registerUser(String userName, String password, String rePassword);

    /**
     * 用户更改密码
     *
     * @param userName      用户名
     * @param password      密码
     * @param newPassword   新密码
     * @param reNewPassword 重新新密码
     * @return
     */
    @CloudFunction("updatePassword")
    ResponseEntity updatePassword(String userName, String password, String newPassword, String reNewPassword);

    /**
     * 获取用户类型
     *
     * @param userId 用户名
     * @return
     */
    @CloudFunction("getUserTypeById")
    ResponseEntity getUserTypeById(String userId);

    /**
     * 获取用户列表
     *
     * @param userDTO 用户信息
     * @return
     */
    @CloudFunction("getUserList")
    ResponseEntity getUserList(UserDTO userDTO);

    /**
     * 获取用户总数
     *
     * @param userDTO 用户信息
     * @return
     */
    @CloudFunction("getUserCount")
    ResponseEntity getUserCount(UserDTO userDTO);

    /**
     * 获取用户类型列表
     *
     * @return
     */
    @CloudFunction("getUserTypeList")
    ResponseEntity getUserTypeList();

    /**
     * 管理用户信息
     *
     * @param userDTO 用户信息
     * @return
     */
    @CloudFunction("manageUserInfo")
    ResponseEntity manageUserInfo(UserDTO userDTO);

    /**
     * 校验用户ID
     *
     * @param userId 用户ID
     * @return
     */
    @CloudFunction("checkUserId")
    ResponseEntity checkUserId(String userId);

    /**
     * 校验用户是否使用
     *
     * @param userId 用户ID
     * @return
     */
    @CloudFunction("checkUser")
    ResponseEntity checkUser(String userId);

    /**
     * 删除用户信息
     *
     * @param userId 用户ID
     * @return
     */
    @CloudFunction("deleteUserById")
    ResponseEntity deleteUserById(String userId);

    /**
     * 管理用户权限
     *
     * @param userDTO 用户信息
     * @return
     */
    @CloudFunction("manageUserAuth")
    ResponseEntity manageUserAuth(UserDTO userDTO);

    /**
     * 获取所有用户
     *
     * @return
     */
    @CloudFunction("getAllUser")
    ResponseEntity getAllUser();
}
