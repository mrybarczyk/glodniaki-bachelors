package jestesmy.glodni.cateringi.configuration;

import org.apache.commons.codec.binary.Hex;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.DigestUtils;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthenticationProviderImplTest {
    @Rule
    public final MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private AccountDetailsService accountDetailsService;

    @InjectMocks
    private AuthenticationProviderImpl authenticationProvider;

    @Test(expected = AuthenticationException.class)
    public void testUserNotFound() {
        // provided
        when(accountDetailsService.loadUserByUsername("admin")).thenThrow(new UsernameNotFoundException(""));
        final Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("admin");

        // when
        authenticationProvider.authenticate(authentication);
    }

    @Test
    public void testUserFound() {
        // provided
        final UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("admin");
        when(userDetails.getPassword()).thenReturn(Hex.encodeHexString(DigestUtils.md5Digest("admin".getBytes())));
        when(accountDetailsService.loadUserByUsername("admin")).thenReturn(userDetails);
        final Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("admin");
        when(authentication.getCredentials()).thenReturn("admin");

        // when
        final Authentication authenticate = authenticationProvider.authenticate(authentication);

        // then
        assertTrue(authenticate.isAuthenticated());
    }
}