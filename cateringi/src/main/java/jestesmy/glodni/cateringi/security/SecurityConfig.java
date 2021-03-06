package jestesmy.glodni.cateringi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsServiceDatabase")
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationProvider authenticationProvider;

    @Autowired
    AuthenticationSuccessHandler successLoginHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login/**","/company/register/**","/client/register/**", "/admin/register/**")
                .permitAll()
                .antMatchers("/")
                .permitAll()
                .antMatchers("/api/companies/**","/admin/**")
                .hasAuthority("ADMIN")
                .antMatchers(("/company/**"))
                .hasAuthority("COMPANY")
                .antMatchers(("/client/**"))
                .hasAuthority("CLIENT")
                .antMatchers("/css/**","/images/**","/scripts/**","*.css","*.js","/resources/**", "static/favicon.ico","/static/**")
                .permitAll()
                .anyRequest()
                .authenticated().and()
                .formLogin().loginPage("/login").successHandler(successLoginHandler)
                .and().logout()
                .and().exceptionHandling().accessDeniedPage("/403")
                .and().csrf().disable();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService)
                .and()
                .authenticationProvider(authenticationProvider);

    }

}
