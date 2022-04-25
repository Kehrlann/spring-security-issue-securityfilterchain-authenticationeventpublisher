package wf.garnier.springsecurityeventpublisher;

import java.util.Collections;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class CommonConfiguration {
	@Bean
	public ApplicationListener<AuthenticationSuccessEvent> listener() {
		return evt -> {
			System.out.println(
					"~~~~~~~> SUCCESSFUL AUTH, type: "
							+ evt.getAuthentication().getClass().getSimpleName()
							+ ", name: " + evt.getAuthentication().getName()
			);
		};
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new InMemoryUserDetailsManager(
				new User("user", "{noop}password", Collections.emptyList())
		);
	}
}
