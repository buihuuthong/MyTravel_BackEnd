package com.BuiHuuThong.MyTravel.dto.response;

import com.BuiHuuThong.MyTravel.common.SoftDeleteDto;
import com.BuiHuuThong.MyTravel.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto extends SoftDeleteDto {
    private String email;
    private String password;
    private String fullName;
    private String phone;
    private String location;
    private String role;

    public UserDto from(User user){
        super.from(user);
        email = user.getEmail();
        password = user.getPassword();
        fullName = user.getFullName();
        phone = user.getPhone();
        location = user.getLocation();
        role = user.getRole();
        return this;
    }
}
