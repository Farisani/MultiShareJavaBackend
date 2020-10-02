package za.co.multishare.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import za.co.multishare.domain.dto.UserLoginInternalDto;
import za.co.multishare.service.UserAuthenticationService;

import java.util.stream.Collectors;

@Service
@Qualifier("customUserDetailsService")
public class SecurityUserDetailsServiceImpl implements UserDetailsService {

    private final UserAuthenticationService userAuthenticationService;

    @Autowired
    public SecurityUserDetailsServiceImpl(final UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserLoginInternalDto userLoginInternalDto = userAuthenticationService.getUserLoginInformation(username);
        return new User(userLoginInternalDto.getUsername(),
                userLoginInternalDto.getPassword(),
                userLoginInternalDto
                        .getRoles()
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()));
    }
}
