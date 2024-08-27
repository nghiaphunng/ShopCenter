package shopcenter.com.response.token;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TokenResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String accessToken; //truy cập hệ thống
	private String refreshToken; // làm mới token
	private Integer userId;
}
