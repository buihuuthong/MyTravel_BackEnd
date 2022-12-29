package com.BuiHuuThong.MyTravel.entity;

import com.BuiHuuThong.MyTravel.common.SoftDeleteEntity;
import lombok.Getter;
import lombok.Setter;
import nonapi.io.github.classgraph.json.Id;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class User extends SoftDeleteEntity {
    @Id
    private Long userId;
    private String uid;
    private String email;
    private String password;
    private String fullName;
    private String phone;
    private String location;
    private String role;
}
