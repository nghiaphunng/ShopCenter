package shopcenter.com.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import shopcenter.com.enums.TokenType;
import shopcenter.com.service.JwtService;
import shopcenter.com.service.UserService;

@Component
@Slf4j
public class PreFilter extends OncePerRequestFilter{
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//hứng các request trước khi xử lý
		log.info("-----------PreFilter-------------");
		
		final String authorization = request.getHeader("Authorization");
//		log.info("Authorization: {}", authorization);
		
		if(StringUtils.isBlank(authorization) || !authorization.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		final String token = authorization.substring("Bearer ".length());
//		log.info("Token: {}", token);
		
		final String userName = jwtService.extractUsername(token, TokenType.ACCESS_TOKEN);
//		log.info("userName: {}", userName);
		
		if(StringUtils.isNotEmpty(userName) && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userName);
			if(jwtService.isValid(token, TokenType.ACCESS_TOKEN,userDetails)) {
				SecurityContext context = SecurityContextHolder.createEmptyContext();
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				context.setAuthentication(authenticationToken);
				SecurityContextHolder.setContext(context);
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
