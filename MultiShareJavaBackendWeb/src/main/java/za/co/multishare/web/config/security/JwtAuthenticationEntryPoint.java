package za.co.multishare.web.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(final HttpServletRequest request,
						 final HttpServletResponse response,
                         final AuthenticationException authException) throws IOException {
		log.debug("Jwt authentication failed:" + authException);
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED	, "Jwt authentication failed");
	}

}