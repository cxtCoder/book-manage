package pers.cxt.bms.client.controller;

import com.hundsun.jrescloud.rpc.annotation.CloudReference;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import pers.cxt.bms.api.annotation.AuthIgnore;
import pers.cxt.bms.api.dto.UserDTO;
import pers.cxt.bms.api.entity.ResponseEntity;
import pers.cxt.bms.api.enums.ErrorCodes;
import pers.cxt.bms.api.service.UserService;
import pers.cxt.bms.api.util.UUIDUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author cxt
 * @Date 2020/6/25
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @CloudReference(springCloud = "book-manage-server")
    private UserService userService;

    /**
     * 用户登录
     *
     * @param userName 用户名
     * @param password 密码
     * @return
     */
    @ApiOperation(value = "用户登录")
    @AuthIgnore
    @PostMapping(value = "/login")
    public ResponseEntity checkLogin(@RequestParam("userName") String userName, @RequestParam("password") String password) {
        try {
            ResponseEntity responseEntity = userService.checkLogin(userName, password);
            if (responseEntity.isSuc()) {
                Map<String, String> map = new HashMap<>(2);
                map.put("userName", userName);
                map.put("token", UUIDUtils.uuid());
                return new ResponseEntity(true, map, null);
            }
            return responseEntity;
        } catch (Exception e) {
            LOGGER.error("UserController checkLogin userName: {}, error: {}", userName, e);
            return new ResponseEntity(ErrorCodes.ERROR_INNER_SYSTEM);
        }
    }

    /**
     * 用户注册
     *
     * @param userName   用户名
     * @param password   密码
     * @param rePassword 重复密码
     * @return
     */
    @ApiOperation(value = "用户注册")
    @AuthIgnore
    @PostMapping(value = "/register")
    public ResponseEntity register(@RequestParam("userName") String userName, @RequestParam("password") String password, @RequestParam("rePassword") String rePassword) {
        try {
            return userService.registerUser(userName, password, rePassword);
        } catch (Exception e) {
            LOGGER.error("UserController register userName: {}, error: {}", userName, e);
            return new ResponseEntity(ErrorCodes.ERROR_INNER_SYSTEM);
        }
    }

    /**
     * 用户更改密码
     *
     * @param userName      用户名
     * @param password      密码
     * @param newPassword   新密码
     * @param reNewPassword 重新新密码
     * @return
     */
    @ApiOperation(value = "用户更改密码")
    @AuthIgnore
    @PostMapping(value = "/updatePassword")
    public ResponseEntity updatePassword(@RequestParam("userName") String userName, @RequestParam("password") String password, @RequestParam("newPassword") String newPassword, @RequestParam("reNewPassword") String reNewPassword) {
        try {
            return userService.updatePassword(userName, password, newPassword, reNewPassword);
        } catch (Exception e) {
            LOGGER.error("UserController updatePassword userName: {}, error: {}", userName, e);
            return new ResponseEntity(ErrorCodes.ERROR_INNER_SYSTEM);
        }
    }

    /**
     * 获取用户类型
     *
     * @param userId 用户名
     * @return
     */
    @ApiOperation(value = "获取用户类型")
    @AuthIgnore
    @PostMapping(value = "/getUserTypeById")
    public ResponseEntity getUserTypeById(@RequestParam("userId") String userId) {
        try {
            return userService.getUserTypeById(userId);
        } catch (Exception e) {
            LOGGER.error("UserController getUserTypeById userId: {}, error: {}", userId, e);
            return new ResponseEntity(ErrorCodes.ERROR_INNER_SYSTEM);
        }
    }

    /**
     * 获取用户列表
     *
     * @param userDTO 用户信息
     * @return
     */
    @ApiOperation(value = "获取用户列表")
    @PostMapping(value = "/getUserList")
    public ResponseEntity getUserList(@RequestBody UserDTO userDTO) {
        try {
            return userService.getUserList(userDTO);
        } catch (Exception e) {
            LOGGER.error("获取用户列表失败", e);
            return new ResponseEntity(ErrorCodes.ERROR_INNER_SYSTEM);
        }
    }

    /**
     * 获取用户总数
     *
     * @param userDTO 用户信息
     * @return
     */
    @ApiOperation(value = "获取用户总数")
    @PostMapping(value = "/getUserCount")
    public ResponseEntity getUserCount(@RequestBody UserDTO userDTO) {
        try {
            return userService.getUserCount(userDTO);
        } catch (Exception e) {
            LOGGER.error("获取用户总数失败", e);
            return new ResponseEntity(ErrorCodes.ERROR_INNER_SYSTEM);
        }
    }

    /**
     * 获取用户类型列表
     *
     * @return
     */
    @ApiOperation(value = "获取用户类型列表")
    @PostMapping(value = "/getUserTypeList")
    public ResponseEntity getUserTypeList() {
        try {
            return userService.getUserTypeList();
        } catch (Exception e) {
            LOGGER.error("获取用户类型列表失败", e);
            return new ResponseEntity(ErrorCodes.ERROR_INNER_SYSTEM);
        }
    }

    /**
     * 管理用户信息
     *
     * @param userDTO 用户信息
     * @return
     */
    @ApiOperation(value = "管理用户信息")
    @PostMapping(value = "/manageUserInfo")
    public ResponseEntity manageUserInfo(@RequestBody UserDTO userDTO) {
        try {
            return userService.manageUserInfo(userDTO);
        } catch (Exception e) {
            LOGGER.error("管理用户信息失败", e);
            return new ResponseEntity(ErrorCodes.ERROR_INNER_SYSTEM);
        }
    }

    /**
     * 校验用户ID
     *
     * @param userId 用户ID
     * @return
     */
    @ApiOperation(value = "校验用户ID")
    @GetMapping(value = "/checkUserId")
    public ResponseEntity checkUserId(@RequestParam("userId") String userId) {
        try {
            return userService.checkUserId(userId);
        } catch (Exception e) {
            LOGGER.error("校验用户ID失败", e);
            return new ResponseEntity(ErrorCodes.ERROR_INNER_SYSTEM);
        }
    }

    /**
     * 校验用户是否使用
     *
     * @param userId 用户ID
     * @return
     */
    @ApiOperation(value = "校验用户是否使用")
    @GetMapping(value = "/checkUser")
    public ResponseEntity checkUser(@RequestParam("userId") String userId) {
        try {
            return userService.checkUser(userId);
        } catch (Exception e) {
            LOGGER.error("校验用户是否使用失败", e);
            return new ResponseEntity(ErrorCodes.ERROR_INNER_SYSTEM);
        }
    }

    /**
     * 删除用户信息
     *
     * @param userId 用户ID
     * @return
     */
    @ApiOperation(value = "删除用户信息")
    @GetMapping(value = "/deleteUserById")
    public ResponseEntity deleteUserById(@RequestParam("userId") String userId) {
        try {
            return userService.deleteUserById(userId);
        } catch (Exception e) {
            LOGGER.error("删除用户信息失败", e);
            return new ResponseEntity(ErrorCodes.ERROR_INNER_SYSTEM);
        }
    }

    /**
     * 管理用户权限
     *
     * @param userDTO 用户信息
     * @return
     */
    @ApiOperation(value = "管理用户权限")
    @PostMapping(value = "/manageUserAuth")
    public ResponseEntity manageUserAuth(@RequestBody UserDTO userDTO) {
        try {
            return userService.manageUserAuth(userDTO);
        } catch (Exception e) {
            LOGGER.error("管理用户权限失败", e);
            return new ResponseEntity(ErrorCodes.ERROR_INNER_SYSTEM);
        }
    }

    /**
     * 获取所有用户
     *
     * @return
     */
    @ApiOperation(value = "获取所有用户")
    @PostMapping(value = "/getAllUser")
    public ResponseEntity getAllUser() {
        try {
            return userService.getAllUser();
        } catch (Exception e) {
            LOGGER.error("获取所有用户失败", e);
            return new ResponseEntity(ErrorCodes.ERROR_INNER_SYSTEM);
        }
    }
}
