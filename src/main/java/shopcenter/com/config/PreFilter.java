package shopcenter.com.config;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PreFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//hứng các request trước khi xử lý
		log.info("-----------PreFilter-------------");
		
		final String authorization = request.getHeader("Authorization");
		log.info("Authorization: {}", authorization);
		
		if(StringUtils.isBlank(authorization) || !authorization.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		final String token = authorization.substring("Bearer ".length());
		log.info("Token: {}", token);
		
		filterChain.doFilter(request, response);
	}

}
