package myapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	/*@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
            	.requestMatchers( "/login").permitAll()            		
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth -> oauth
                    .loginPage("/login")  // 自作のログインページ
                    .defaultSuccessUrl("/")  // ログイン成功後のリダイレクト先
                )
            .rememberMe(Customizer.withDefaults());

        return http.build();
    }
}