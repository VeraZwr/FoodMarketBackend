package soen487.foodmarket.service;

import org.springframework.validation.BindingResult;
import soen487.foodmarket.models.ChangePassword;
import soen487.foodmarket.models.UserModel;

public interface UserService {

    UserModel createUser(UserModel userModel);

    UserModel login(String username, String password);

    UserModel changePassword(ChangePassword changePassword);

    UserModel changeInfo(UserModel userModel);

    UserModel findById(Integer id);
}
