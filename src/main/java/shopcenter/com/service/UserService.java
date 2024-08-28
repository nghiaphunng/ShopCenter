package shopcenter.com.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import shopcenter.com.entity.User;

public interface UserService {
	UserDetailsService userDetailsService();
	User getUserByEmail(String email);
}
