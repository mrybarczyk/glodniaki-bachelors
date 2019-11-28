package jestesmy.glodni.cateringi.configuration;

import com.sun.net.httpserver.HttpsConfigurator;
import jestesmy.glodni.cateringi.controller.web.authentication.AccountDetailsService;
import jestesmy.glodni.cateringi.controller.web.authentication.AuthenticationProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AccountDetailsService accountDetailsService;

    @Autowired
    AuthenticationProvider authenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login/**")
                .permitAll()
                .anyRequest()
                .authenticated().and()
                .formLogin().loginPage("/login").loginProcessingUrl("/perform_login").defaultSuccessUrl("/").failureForwardUrl("/error")
                .and().csrf().disable();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(accountDetailsService)
                .and()
                .authenticationProvider(authenticationProvider);

    }

}
