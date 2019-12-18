package jestesmy.glodni.cateringi.security;

import jestesmy.glodni.cateringi.model.User;
import jestesmy.glodni.cateringi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceDatabase")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String accountName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(accountName);
        if(user==null)
            throw new UsernameNotFoundException("Podane konto nie istnieje");
        return new UserDetailsImpl(user);
    }
}
