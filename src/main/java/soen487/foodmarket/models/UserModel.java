package soen487.foodmarket.models;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserModel {

    private Integer id;

    @NotNull(message = "username cannot be empty")
    @Size(min=3, max=50, message = "The length of username must be 3-50")
    private String username;

    @NotNull(message = "password cannot be empty")
    @Size(min=6, max=50, message = "The length of password must be 6-50")
    private String password;

    @NotNull(message = "name cannot be empty")
    @Size(min=2, max=100, message = "The length of name must be 2-100")
    private String name;

    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Telephone number cannot be empty")
    private String tel;

    @NotNull(message = "address cannot be empty")
    private String address;
}
