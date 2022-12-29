package com.BuiHuuThong.MyTravel.dto.request;

import com.BuiHuuThong.MyTravel.util.NullOrNotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDto {
    @NullOrNotBlank(message = "Thiếu địa chỉ email")
    private String email;
    @NullOrNotBlank(message = "Thiếu mật khẩu")
    private String password;
    @NullOrNotBlank(message = "Thiếu tên người dùng")
    private String fullName;
    @NullOrNotBlank(message = "Thiếu số điện thoại")
    private String phone;
    private String location;
    private String role;
}
