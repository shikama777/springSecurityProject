package myapp.handler;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import myapp.service.SessionService;

@Component
public class LoginHandler implements AuthenticationSuccessHandler  {
	
	@Autowired
	private SessionService sessionService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response,
			org.springframework.security.core.Authentication authentication)
			throws ServletException {
		
		Object principal = authentication.getPrincipal();

		if (principal instanceof UserDetails) {
			handleDatabaseLogin((UserDetails) principal);
		} else if (principal instanceof OAuth2User) {
			handleOAuthLogin((OAuth2User) principal);
		}

		try {
			response.sendRedirect("/");
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
	
	private void handleDatabaseLogin(UserDetails user) {
		sessionService.setUserName(user.getUsername());
	}
	
	private void handleOAuthLogin(OAuth2User user) {
		sessionService.setUserName(user.getAttribute("email"));
	}
}
	
