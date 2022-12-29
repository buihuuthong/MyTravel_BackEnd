package com.BuiHuuThong.MyTravel.service;

import com.BuiHuuThong.MyTravel.entity.User;
import com.BuiHuuThong.MyTravel.exception.ApiException;
import com.BuiHuuThong.MyTravel.exception.ErrorCode;
import com.BuiHuuThong.MyTravel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Override
    public User layUser(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "Tài khoản không tồn tại"));
    }

}
