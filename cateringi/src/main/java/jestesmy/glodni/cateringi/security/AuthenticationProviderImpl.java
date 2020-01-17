package jestesmy.glodni.cateringi.security;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.AccountNotFoundException;
import java.util.Collections;

@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {

    @Autowired
    @Qualifier("userDetailsServiceDatabase")
    UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails accountDetails = userDetailsService.loadUserByUsername(authentication.getName());
        if(Hex.encodeHexString(DigestUtils.md5Digest(authentication.getCredentials().toString().getBytes())).equals(accountDetails.getPassword())){
            if (accountDetails.isAccountNonLocked()) return new UsernamePasswordAuthenticationToken(accountDetails.getUsername(),accountDetails.getPassword(), accountDetails.getAuthorities());
            else {
                throw new BadCredentialsException("Your account is suspended.");
            }
        } else {
            throw new BadCredentialsException("Authentication failed");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
