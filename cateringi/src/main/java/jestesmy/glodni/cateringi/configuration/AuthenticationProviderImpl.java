package jestesmy.glodni.cateringi.configuration;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.util.Collections;

@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {

    @Autowired
    AccountDetailsService accountDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails accountDetails = accountDetailsService.loadUserByUsername(authentication.getName());
        if(Hex.encodeHexString(DigestUtils.md5Digest(authentication.getCredentials().toString().getBytes())).equals(accountDetails.getPassword())){
            return new UsernamePasswordAuthenticationToken(accountDetails.getUsername(),accountDetails.getPassword(), Collections.emptyList());
        } else {
            throw new BadCredentialsException("Authentication failed");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
