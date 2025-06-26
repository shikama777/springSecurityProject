package myapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import myapp.entity.User;
import myapp.repository.UserRepository;

@Service
public class LoginUserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserLoginId(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String role = "";
        if (user.getRole().equals("1")) {
        	role = "ROLE_ADMIN";
        } else if (user.getRole().equals("2")) {
        	role = "ROLE_USER";
        } 

        return new org.springframework.security.core.userdetails.User(
                user.getUserLoginId(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                AuthorityUtils.createAuthorityList(role)
        );
    }

}
