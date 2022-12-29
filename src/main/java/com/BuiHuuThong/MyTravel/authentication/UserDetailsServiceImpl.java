package com.BuiHuuThong.MyTravel.authentication;

import com.BuiHuuThong.MyTravel.common.UsernameEncoder;
import com.BuiHuuThong.MyTravel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var usernameEncoder = UsernameEncoder.decode(username);
		if (usernameEncoder == null) {
			throw new UsernameNotFoundException("Có lỗi khi kiểm tra tài khoản");
		}
		var tenDangNhap = usernameEncoder.email();
		return loadUserDetails(tenDangNhap);
	}
	private UserDetails loadUserDetails(String email) {
//		var user = userRepository.findByEmail(email)
//			.orElseThrow(() -> new UsernameNotFoundException("Tài khoản khách hàng không tồn tại"));
//		return new org.springframework.security.core.userdetails.User(
//			email,
//			user.getPassword()
//		);
		return null;
	}
}
