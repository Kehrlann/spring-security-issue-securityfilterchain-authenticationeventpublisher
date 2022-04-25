package wf.garnier.springsecurityeventpublisher;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

class AuthenticationEventTests {

	private final SpringApplicationBuilder builder = new SpringApplicationBuilder(
			SpringSecurityEventPublisherApplication.class,
			TestEventListener.class
	);

	@Test
	void withWebSecurityConfigurerAdapter() {
		try (var app = builder.profiles("adapter").run()) {
			httpBasicRequest("user", "password");
			httpBasicRequest("foo", "bar");
			var eventListener = app.getBean(TestEventListener.class);
			assertThat(eventListener.getSuccessfulAuthentications()).containsExactly("user", "foo");
		}
	}


	@Test
	void withSecurityFilterChainBean() {
		try (var app = builder.profiles("filterchain").run()) {
			httpBasicRequest("user", "password");
			httpBasicRequest("foo", "bar");
			var eventListener = app.getBean(TestEventListener.class);
			assertThat(eventListener.getSuccessfulAuthentications()).containsExactly("user", "foo");
		}
	}

	private void httpBasicRequest(String username, String password) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth(username, password);
		var entity = new HttpEntity<String>(headers);
		try {
			new RestTemplate()
					.exchange("http://localhost:8080/", HttpMethod.GET, entity, String.class);
		} catch (HttpStatusCodeException ignored) {
			// ignored
		}
	}

	static class TestEventListener implements ApplicationListener<AuthenticationSuccessEvent> {
		private final List<String> successfulAuthentications = new ArrayList<>();

		@Override
		public void onApplicationEvent(AuthenticationSuccessEvent event) {
			successfulAuthentications.add(event.getAuthentication().getName());
		}

		public List<String> getSuccessfulAuthentications() {
			return successfulAuthentications;
		}
	}

}
