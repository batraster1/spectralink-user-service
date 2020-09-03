package com.spectralink.user.web.rest;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing global OIDC logout.
 */
@RestController
public class LogoutResource {
	private final ClientRegistration registration;

	public LogoutResource(ClientRegistrationRepository registrations) {
		this.registration = registrations.findByRegistrationId("oidc");
	}

	/**
	 * {@code POST  /api/logout} : logout the current user.
	 *
	 * @param request the {@link HttpServletRequest}.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and a body
	 *         with a global logout URL and ID token.
	 */
	@PostMapping("/api/logout")
	public ResponseEntity<?> logout(HttpServletRequest request) {
		Map<String, String> logoutDetails = new HashMap<>();
		logoutDetails.put("logoutUrl", "https://spectralink-user-service.auth.us-east-2.amazoncognito.com/logout");
		logoutDetails.put("clientId", registration.getClientId());
		request.getSession().invalidate();
		return ResponseEntity.ok().body(logoutDetails);
	}
}
