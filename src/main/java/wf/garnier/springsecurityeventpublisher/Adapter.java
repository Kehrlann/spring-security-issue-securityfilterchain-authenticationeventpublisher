package wf.garnier.springsecurityeventpublisher;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.springframework.security.config.Customizer.withDefaults;

@Profile("adapter")
@Configuration
public class Adapter extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.authenticationProvider(new FooUserAuthenticationProvider())
			.authorizeHttpRequests(req -> req.anyRequest().authenticated())
			.httpBasic(withDefaults());
		// @formatter:on
	}
}
