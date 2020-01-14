package jestesmy.glodni.cateringi.security;

import jestesmy.glodni.cateringi.domain.model.User;
import jestesmy.glodni.cateringi.domain.model.UserType;
import jestesmy.glodni.cateringi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentAuthenticatedUserService {

    @Autowired
    UserRepository userRepository;


    public String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal().toString();
    }

    public User getCurrentUser() {
        return userRepository.findByUserName(getCurrentUserName());
    }

    private boolean isClient() {
        return getCurrentUser().getUserType() == UserType.CLIENT;
    }

    private boolean isCompany() {
        return getCurrentUser().getUserType() == UserType.COMPANY;
    }

    private boolean isAdmin() {
        return getCurrentUser().getUserType() == UserType.ADMIN;
    }

}
