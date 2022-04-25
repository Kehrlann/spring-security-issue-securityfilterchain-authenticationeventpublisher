package wf.garnier.springsecurityeventpublisher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Profile("filterchain")
@Configuration
public class FilterChainBean {
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// @formatter:off
		return http
				.authenticationProvider(new FooUserAuthenticationProvider())
				.authorizeHttpRequests(req -> req.anyRequest().authenticated())
				.httpBasic(withDefaults())
				.build();
		// @formatter:on
	}
}

