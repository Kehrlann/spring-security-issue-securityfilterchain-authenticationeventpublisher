package wf.garnier.springsecurityeventpublisher;

import java.util.Collections;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Custom authentication provider, user `foo` can log in with any password.
 */
public class FooUserAuthenticationProvider implements AuthenticationProvider {
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if ("foo".equals(authentication.getName())) {
			return new UsernamePasswordAuthenticationToken(
					"foo",
					null,
					Collections.singleton(new SimpleGrantedAuthority("ROLE_admin"))
			);
		} else {
			return null;
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
