package shopcenter.com.config;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Value("${jwt.signerKey}")
	private String signKey;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
		httpSecurity
			.authorizeHttpRequests(request ->
					request.requestMatchers(HttpMethod.GET, "/home/**").permitAll()
							.requestMatchers(HttpMethod.POST, "/home/**").permitAll()
							.requestMatchers(HttpMethod.POST, "/register").permitAll()
							
							.requestMatchers(HttpMethod.GET, "/shop/**").permitAll()
							.requestMatchers(HttpMethod.POST, "/shop/**").permitAll()
							
							.anyRequest().authenticated()
			)
			
			.oauth2ResourceServer(oauth2 ->
				oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder()))
						.authenticationEntryPoint(new JwtAuthenticationEntryPoint())
					)
					
			.csrf(AbstractHttpConfigurer::disable);
		
		return httpSecurity.build();
	}
	
	@Bean
	JwtDecoder jwtDecoder() {
		SecretKeySpec secretKeySpec = new SecretKeySpec(signKey.getBytes(), "HS512");
		return NimbusJwtDecoder
					.withSecretKey(secretKeySpec)
					.macAlgorithm(MacAlgorithm.HS512)
					.build();
	}
}
