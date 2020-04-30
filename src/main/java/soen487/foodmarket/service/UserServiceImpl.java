package soen487.foodmarket.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soen487.foodmarket.dataobject.UserInfo;
import soen487.foodmarket.error.BusinessException;
import soen487.foodmarket.error.EmBusinessError;
import soen487.foodmarket.models.ChangePassword;
import soen487.foodmarket.models.UserModel;
import soen487.foodmarket.repository.UserRepository;



@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserModel createUser(UserModel userModel) {
        UserInfo byUsername = userRepository.findByUsername(userModel.getUsername());
        if (byUsername != null) {
            log.error("[User Creation]user name already exists. username={}", userModel.getUsername());
            throw new BusinessException(EmBusinessError.USER_NAME_EXISTS);
        }
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userModel, userInfo);

        UserInfo save = userRepository.save(userInfo);
        userModel.setId(save.getId());
        return userModel;
    }

    @Override
    public UserModel login(String username, String password) {
        UserInfo userInfo = findUsernameAndCheckPassword(username, password);
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userInfo, userModel);
        return userModel;
    }

    private UserInfo findUsernameAndCheckPassword(String username, String password) {
        UserInfo userInfo = userRepository.findByUsername(username);
        if (userInfo == null || !userInfo.getPassword().equals(password)) {
            log.error("[login] password incorrect. password={}", password);
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        return userInfo;
    }

    @Override
    public UserModel changePassword(ChangePassword changePassword) {
        UserInfo userInfo = findUsernameAndCheckPassword(changePassword.getUsername(),
                changePassword.getPassword());
        userInfo.setPassword(changePassword.getNewPassword());
        UserInfo save = userRepository.save(userInfo);
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(save, userModel);
        return userModel;
    }

    @Override
    public UserModel changeInfo(UserModel userModel) {
        UserInfo userInfo = userRepository.findById(userModel.getId()).orElse(null);
        if (userInfo == null) {
            log.error("[User info change] User does not exist. user={}", userModel);
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        if (!userModel.getUsername().equals(userInfo.getUsername()) ||
                !userModel.getPassword().equals(userInfo.getPassword())) {
            log.error("[User info change] You cannot change username or password here. user={}", userModel);
            throw new BusinessException(EmBusinessError.USER_INFO_INCORRECT);
        }
        UserInfo newUser = new UserInfo();
        BeanUtils.copyProperties(userModel, newUser);
        newUser.setCreateTime(userInfo.getCreateTime());
        newUser.setUpdateTime(userInfo.getUpdateTime());
        userRepository.save(newUser);
        return userModel;
    }

    @Override
    public UserModel findById(Integer id) {
        UserInfo userInfo = userRepository.findById(id).orElse(null);
        if (userInfo == null) {
            log.error("[User info change] User does not exist. id={}", id);
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userInfo, userModel);
        return userModel;
    }
}
