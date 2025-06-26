package myapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import myapp.handler.LoginHandler;
import myapp.service.CustomOAuth2UserService;
import myapp.service.LoginUserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private CustomOAuth2UserService customOAuth2UserService;
	
	@Autowired
	private LoginHandler loginHandler;

	@Autowired
    private LoginUserService loginUserService;

	@Bean
	PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
            	.requestMatchers( "/login").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")         // ROLE_ADMIN が必要
                .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN") // ROLE_USER または ROLE_ADMIN
                .anyRequest().authenticated()
            )
            .formLogin(formLogin -> formLogin
            		.loginPage("/login")  // 共通ログインページでもOK
                    .defaultSuccessUrl("/")
                    .successHandler(loginHandler)
                    .permitAll()
            )
            .oauth2Login(oauth -> oauth
                    .loginPage("/login")  // 自作のログインページ
                    .successHandler(loginHandler)
                )
            .rememberMe(Customizer.withDefaults());

        return http.build();
    }
}