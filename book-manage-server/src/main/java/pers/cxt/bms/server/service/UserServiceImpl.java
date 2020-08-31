package pers.cxt.bms.server.service;

import com.hundsun.jrescloud.rpc.annotation.CloudComponent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import pers.cxt.bms.api.bo.NameIdBO;
import pers.cxt.bms.api.constants.UserConstant;
import pers.cxt.bms.api.dto.UserDTO;
import pers.cxt.bms.api.entity.ResponseEntity;
import pers.cxt.bms.api.enums.ErrorCodes;
import pers.cxt.bms.api.service.UserService;
import pers.cxt.bms.api.util.MD5Utils;
import pers.cxt.bms.database.dao.BmBookMapper;
import pers.cxt.bms.database.dao.BmUserMapper;
import pers.cxt.bms.database.dao.BmUserTypeMapper;
import pers.cxt.bms.database.entity.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author cxt
 * @Date 2020/6/25
 */
@Slf4j
@CloudComponent
public class UserServiceImpl implements UserService {

    @Autowired
    private BmUserMapper userMapper;

    @Autowired
    private BmUserTypeMapper userTypeMapper;

    @Autowired
    private BmBookMapper bookMapper;

    /**
     * 用户登录
     *
     * @param userName 用户名
     * @param password 密码
     * @return
     */
    @Override
    public ResponseEntity checkLogin(String userName, String password) {
        if (StringUtils.isBlank(userName)) {
            return new ResponseEntity(ErrorCodes.USERNAME_IS_EMPTY);
        }
        if (StringUtils.isBlank(password)) {
            return new ResponseEntity(ErrorCodes.PASSWORD_IS_EMPTY);
        }
        BmUser user = userMapper.selectByPrimaryKey(userName);
        if (user == null) {
            return new ResponseEntity(ErrorCodes.USERNAME_ERROR);
        }
        if (!password.equals(user.getUserPwd())) {
            return new ResponseEntity(ErrorCodes.PASSWORD_ERROR);
        }
        return new ResponseEntity(true, null, UserConstant.LOGIN_SUCCESS);
    }

    /**
     * 用户注册
     *
     * @param userName   用户名
     * @param password   密码
     * @param rePassword 重复密码
     * @return
     */
    @Override
    public ResponseEntity registerUser(String userName, String password, String rePassword) {
        if (StringUtils.isBlank(userName)) {
            return new ResponseEntity(ErrorCodes.USERNAME_IS_EMPTY);
        }
        if (StringUtils.isBlank(password)) {
            return new ResponseEntity(ErrorCodes.PASSWORD_IS_EMPTY);
        }
        if (StringUtils.isBlank(rePassword)) {
            return new ResponseEntity(ErrorCodes.REPASSWORD_IS_EMPTY);
        }
        if (userName.length() > 20) {
            return new ResponseEntity(ErrorCodes.USERNAME_IS_TOO_LONG);
        }
        if (password.length() > 30) {
            return new ResponseEntity(ErrorCodes.PASSWORD_IS_TOO_LONG);
        }
        if (!password.equals(rePassword)) {
            return new ResponseEntity(ErrorCodes.PASSWORD_REPASSWORD_IS_NOT_SAME);
        }
        BmUser user = userMapper.selectByPrimaryKey(userName);
        if (user != null) {
            return new ResponseEntity(ErrorCodes.USERNAME_IS_EXIST);
        }
        BmUser newUser = new BmUser();
        newUser.setUserId(userName);
        newUser.setUserPwd(MD5Utils.GetMD5Code(password));
        newUser.setTypeId(UserConstant.OWN_USER);
        newUser.setCreateTime(new Date());
        newUser.setUpdateTime(new Date());
        userMapper.insert(newUser);
        return new ResponseEntity(true, null, UserConstant.REGISTER_SUCCESS);
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
    @Override
    public ResponseEntity updatePassword(String userName, String password, String newPassword, String reNewPassword) {
        if (StringUtils.isBlank(userName)) {
            return new ResponseEntity(ErrorCodes.USERNAME_IS_EMPTY);
        }
        BmUser user = userMapper.selectByPrimaryKey(userName);
        if (user == null) {
            return new ResponseEntity(ErrorCodes.USERNAME_ERROR);
        }
        if (StringUtils.isBlank(password)) {
            return new ResponseEntity(ErrorCodes.PASSWORD_IS_EMPTY);
        }
        if (!password.equals(user.getUserPwd())) {
            return new ResponseEntity(ErrorCodes.OR_PASSWORD_ERROR);
        }
        if (StringUtils.isBlank(newPassword)) {
            return new ResponseEntity(ErrorCodes.PASSWORD_IS_EMPTY);
        }
        if (StringUtils.isBlank(reNewPassword)) {
            return new ResponseEntity(ErrorCodes.REPASSWORD_IS_EMPTY);
        }
        if (newPassword.length() > 30) {
            return new ResponseEntity(ErrorCodes.PASSWORD_IS_TOO_LONG);
        }
        if (reNewPassword.length() > 30) {
            return new ResponseEntity(ErrorCodes.PASSWORD_IS_TOO_LONG);
        }
        if (!newPassword.equals(reNewPassword)) {
            return new ResponseEntity(ErrorCodes.PASSWORD_REPASSWORD_IS_NOT_SAME);
        }
        BmUser newUser = new BmUser();
        newUser.setUserId(userName);
        newUser.setUserPwd(MD5Utils.GetMD5Code(newPassword));
        newUser.setUpdateTime(new Date());
        userMapper.updateByPrimaryKeySelective(newUser);
        return new ResponseEntity(true, null, UserConstant.UPDATE_PASSWORD_SUCCESS);
    }

    /**
     * 获取用户类型
     *
     * @param userId 用户名
     * @return
     */
    @Override
    public ResponseEntity getUserTypeById(String userId) {
        BmUser user = userMapper.selectByPrimaryKey(userId);
        return new ResponseEntity(true, user.getTypeId(), null);
    }

    /**
     * 获取用户列表
     *
     * @param userDTO 用户信息
     * @return
     */
    @Override
    public ResponseEntity getUserList(UserDTO userDTO) {
        List<UserDTO> userDTOList = new ArrayList<>();
        BmUserCriteria userCriteria = setUserCriteria(userDTO);
        RowBounds rowBounds = new RowBounds(userDTO.getPageSize() * (userDTO.getPageNum() - 1), userDTO.getPageSize());
        List<BmUser> userList = userMapper.selectByExampleWithRowbounds(userCriteria, rowBounds);
        userList.forEach(user -> {
            UserDTO dto = setUserInfo(user);
            userDTOList.add(dto);
        });
        return new ResponseEntity(true, userDTOList, null);
    }

    /**
     * 获取用户总数
     *
     * @param userDTO 用户信息
     * @return
     */
    @Override
    public ResponseEntity getUserCount(UserDTO userDTO) {
        BmUserCriteria userCriteria = setUserCriteria(userDTO);
        return new ResponseEntity(true, userMapper.countByExample(userCriteria), null);
    }

    /**
     * 获取用户类型列表
     *
     * @return
     */
    @Override
    public ResponseEntity getUserTypeList() {
        List<NameIdBO> nameIdBOList = new ArrayList<>();
        List<BmUserType> userTypeList = userTypeMapper.selectByExample(new BmUserTypeCriteria());
        userTypeList.forEach(type -> {
            NameIdBO nameIdBO = new NameIdBO();
            nameIdBO.setId(type.getTypeId());
            nameIdBO.setName(type.getTypeName());
            nameIdBOList.add(nameIdBO);
        });
        return new ResponseEntity(true, nameIdBOList, null);
    }

    /**
     * 管理用户信息
     *
     * @param userDTO 用户信息
     * @return
     */
    @Override
    public ResponseEntity manageUserInfo(UserDTO userDTO) {
        BmUser user = new BmUser();
        user.setUserId(userDTO.getUserId());
        user.setUserName(userDTO.getUserName());
        user.setUpdateTime(new Date());
        if (StringUtils.isBlank(userDTO.getTypeId())) {
            user.setUserPwd(MD5Utils.GetMD5Code(UserConstant.DEFAULT_PASSWORD));
            user.setTypeId(UserConstant.OWN_USER);
            user.setCreateTime(new Date());
            userMapper.insert(user);
        } else {
            userMapper.updateByPrimaryKeySelective(user);
        }
        return new ResponseEntity(true, null, null);
    }

    /**
     * 校验用户ID
     *
     * @param userId 用户ID
     * @return
     */
    @Override
    public ResponseEntity checkUserId(String userId) {
        boolean isExist = false;
        BmUser user = userMapper.selectByPrimaryKey(userId);
        if (user == null) {
            isExist = true;
        }
        return new ResponseEntity(true, isExist, null);
    }

    /**
     * 校验用户是否使用
     *
     * @param userId 用户ID
     * @return
     */
    @Override
    public ResponseEntity checkUser(String userId) {
        boolean isUsed = false;
        BmBookCriteria bookCriteria = new BmBookCriteria();
        bookCriteria.createCriteria().andUserIdEqualTo(userId);
        long count = bookMapper.countByExample(bookCriteria);
        if (count == 0) {
            isUsed = true;
        }
        return new ResponseEntity(true, isUsed, null);
    }

    /**
     * 删除用户信息
     *
     * @param userId 用户ID
     * @return
     */
    @Override
    public ResponseEntity deleteUserById(String userId) {
        userMapper.deleteByPrimaryKey(userId);
        return new ResponseEntity(true, null, null);
    }

    /**
     * 管理用户权限
     *
     * @param userDTO 用户信息
     * @return
     */
    @Override
    public ResponseEntity manageUserAuth(UserDTO userDTO) {
        BmUser user = new BmUser();
        user.setUserId(userDTO.getUserId());
        user.setTypeId(userDTO.getTypeId());
        user.setUpdateTime(new Date());
        userMapper.updateByPrimaryKeySelective(user);
        return new ResponseEntity(true, null, null);
    }

    /**
     * 获取所有用户
     *
     * @return
     */
    @Override
    public ResponseEntity getAllUser() {
        List<BmUser> userList = userMapper.selectByExample(new BmUserCriteria());
        List<NameIdBO> nameIdBOList = new ArrayList<>(userList.size());
        userList.forEach(user -> {
            if (StringUtils.isNotBlank(user.getUserName())) {
                NameIdBO nameIdBO = new NameIdBO();
                nameIdBO.setId(user.getUserId());
                nameIdBO.setName(user.getUserName());
                nameIdBOList.add(nameIdBO);
            }
        });
        return new ResponseEntity(true, nameIdBOList, null);
    }

    /**
     * 设置查询条件
     *
     * @param userDTO
     * @return
     */
    private BmUserCriteria setUserCriteria(UserDTO userDTO) {
        BmUserCriteria userCriteria = new BmUserCriteria();
        BmUserCriteria.Criteria criteria = userCriteria.createCriteria();
        if (StringUtils.isNotBlank(userDTO.getUserId())) {
            criteria.andUserIdLike("%" + userDTO.getUserId() + "%");
        }
        if (StringUtils.isNotBlank(userDTO.getUserName())) {
            criteria.andUserNameLike("%" + userDTO.getUserName() + "%");
        }
        if (StringUtils.isNotBlank(userDTO.getTypeId())) {
            criteria.andTypeIdEqualTo(userDTO.getTypeId());
        }
        return userCriteria;
    }

    /**
     * 设置用户信息
     *
     * @param user
     * @return
     */
    private UserDTO setUserInfo(BmUser user) {
        BmUserType userType = userTypeMapper.selectByPrimaryKey(user.getTypeId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUserName(user.getUserName());
        userDTO.setTypeId(user.getTypeId());
        userDTO.setTypeName(userType.getTypeName());
        userDTO.setCreateTime(sdf.format(user.getCreateTime()));
        userDTO.setUpdateTime(sdf.format(user.getUpdateTime()));
        return userDTO;
    }
}
