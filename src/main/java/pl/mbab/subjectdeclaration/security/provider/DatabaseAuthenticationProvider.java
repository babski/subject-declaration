package pl.mbab.subjectdeclaration.security.provider;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import pl.mbab.subjectdeclaration.service.UserService;

@Component
public class DatabaseAuthenticationProvider implements AuthenticationProvider {

    private UserService userService;
    private UserDetailsService userDetailsService;

    @Autowired
    public DatabaseAuthenticationProvider(@Qualifier("userInfoServiceImpl") UserDetailsService userDetailsService, UserService userService) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        System.out.println(email);
        String password = DigestUtils.md5Hex(authentication.getCredentials().toString());
        if (userService.authenticate(email, password)) {
            UserDetails user = userDetailsService.loadUserByUsername(email);
            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
                UsernamePasswordAuthenticationToken.class);
    }
}