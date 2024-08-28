package shopcenter.com.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shopcenter.com.entity.Token;
import shopcenter.com.exception.AppException;
import shopcenter.com.exception.ErrorCode;
import shopcenter.com.repository.TokenRepository;
import shopcenter.com.service.TokenService;

@Service
public class TokenServiceImpl implements TokenService{
	@Autowired
	private TokenRepository tokenRepository;
	
	@Override
	public int save(Token token) {
		Optional<Token> optional = tokenRepository.findByUsername(token.getUsername());
		
		if(optional.isEmpty()) { //nếu chưa có trong DB -> lưu vào DB
			tokenRepository.save(token);
			return token.getTokenId();
		}
		else {  //nếu đã có trong DB -> lưu cập nhật vào DB
			Token currentToken = optional.get();
			currentToken.setAccessToken(token.getAccessToken());
			currentToken.setRefreshToken(token.getRefreshToken());
			tokenRepository.save(currentToken);
			return currentToken.getTokenId();
		}
	}

	@Override
	public String delete(Token token) {
		tokenRepository.delete(token);
		return "Deleted!";
	}

	@Override
	public Token getByUsername(String username) {
		return tokenRepository.findByUsername(username)
					.orElseThrow(() -> new AppException(ErrorCode.TOKEN_NOT_EXISTED));
	}

}
