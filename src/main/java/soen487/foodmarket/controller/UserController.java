package soen487.foodmarket.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import soen487.foodmarket.error.BusinessException;
import soen487.foodmarket.error.EmBusinessError;
import soen487.foodmarket.models.ChangePassword;
import soen487.foodmarket.models.CommonReturnType;
import soen487.foodmarket.models.UserModel;
import soen487.foodmarket.service.EmailSender;
import soen487.foodmarket.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@Slf4j
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class UserController {

    private final UserService userService;

    private final EmailSender emailSender;

    @Autowired
    public UserController(UserService userService, EmailSender emailSender) {
        this.userService = userService;
        this.emailSender = emailSender;
    }

    @GetMapping(value = "/mail")
    public CommonReturnType test() {
        emailSender.sendSimpleMail("jingchao.zhang@mail.concordia.ca", "Food Market", "test");
        return CommonReturnType.create(null);
    }

    @PostMapping(value = "/new")
    public CommonReturnType create(@Valid @RequestBody UserModel userModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("[User Creation] Parameter invalid, user={}", userModel);
            if (bindingResult.getFieldError() == null) {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
            }
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,
                    bindingResult.getFieldError().getDefaultMessage());
        }
        UserModel user = userService.createUser(userModel);
        return CommonReturnType.create(user);
    }

    @PostMapping(value = "/login")
    public CommonReturnType login(@RequestParam String username, @RequestParam String password) {
        UserModel userModel = userService.login(username, password);
        return CommonReturnType.create(userModel);
    }

    @PutMapping(value = "/pwdchange")
    public CommonReturnType changePassword(@Valid @RequestBody ChangePassword changePassword, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("[Change password] user, changePassword={}", changePassword);
            if (bindingResult.getFieldError() == null) {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
            }
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,
                    bindingResult.getFieldError().getDefaultMessage());
        }
        UserModel userModel = userService.changePassword(changePassword);
        return CommonReturnType.create(userModel);
    }

    @PutMapping(value = "/infochange")
    public CommonReturnType changeInfo(@Valid @RequestBody UserModel userModel, BindingResult bindingResult) {
        if (userModel.getId() == null) {
            log.error("[User info change] user id must not be null, user={}", userModel );
            throw new BusinessException(EmBusinessError.USER_ID_NULL);
        }
        if (bindingResult.hasErrors()) {
            log.error("[User info change] New info invalid, user={}", userModel);
            if (bindingResult.getFieldError() == null) {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
            }
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,
                    bindingResult.getFieldError().getDefaultMessage());
        }
        UserModel newUserModel = userService.changeInfo(userModel);
        return CommonReturnType.create(newUserModel);
    }

}
