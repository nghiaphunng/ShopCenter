package shopcenter.com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import shopcenter.com.exception.AppException;
import shopcenter.com.exception.ErrorCode;
import shopcenter.com.repository.UserRepository;
import shopcenter.com.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetailsService userDetailsService() {
		return username -> userRepository.findByUserName(username)
								.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
	}

}
