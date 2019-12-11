package jestesmy.glodni.cateringi.security;

import jestesmy.glodni.cateringi.model.UserType;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

@Configuration
public class SuccessLoginHandlerImpl implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        Set<String> type = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if(type.contains(UserType.ADMIN.toString())){
            response.sendRedirect("/admin");
        }
        else if(type.contains(UserType.CLIENT.toString())) {
            response.sendRedirect("/client");
        }
        else if(type.contains(UserType.COMPANY.toString())) {
            response.sendRedirect("/company");
        }
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        Set<String> type = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if(type.contains(UserType.ADMIN.toString())){
            httpServletResponse.sendRedirect("/admin");
        }
        else if(type.contains(UserType.CLIENT.toString())) {
            httpServletResponse.sendRedirect("/client");
        }
        else if(type.contains(UserType.COMPANY.toString())) {
            httpServletResponse.sendRedirect("/company");
        }
    }
}