package shopcenter.com.service;

import shopcenter.com.entity.Token;

public interface TokenService {
	int save(Token token);
	String delete(Token token);
	Token getByUsername(String username);
}	
