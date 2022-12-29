package com.BuiHuuThong.MyTravel.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CreateUserDto {
    @NotBlank(message = "Thiếu địa chỉ email")
    private String email;
    @NotBlank(message = "Thiếu mật khẩu")
    private String password;
    @NotBlank(message = "Thiếu tên người dùng")
    private String fullName;
    @NotBlank(message = "Thiếu số điện thoại")
    private String phone;
    private String location;
    private String role;
}
