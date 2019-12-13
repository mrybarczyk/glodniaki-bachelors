package jestesmy.glodni.cateringi;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Collections;


public class UserDetailsSecurityContextFactory implements WithSecurityContextFactory<WithMockedUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockedUser withMockedUser) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(withMockedUser.username(),"admin", Collections.emptyList());
        context.setAuthentication(authentication);
        return context;
    }
}
