package soen487.foodmarket.models;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ChangePassword {

    @NotNull(message = "Username cannot be empty")
    @Size(min=3, max=50, message = "The length of username must be 3-50")
    private String username;

    @NotNull(message = "Password cannot be empty")
    @Size(min=6, max=50, message = "The length of password must be 6-50")
    private String password;

    @NotNull(message = "Password cannot be empty")
    @Size(min=6, max=50, message = "The length of password must be 6-50")
    private String newPassword;

}
