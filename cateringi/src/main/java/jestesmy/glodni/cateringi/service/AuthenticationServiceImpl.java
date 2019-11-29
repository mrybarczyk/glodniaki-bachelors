package jestesmy.glodni.cateringi.service;

import jestesmy.glodni.cateringi.model.Account;
import jestesmy.glodni.cateringi.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AccountRepository accountRepository;

    @Autowired
    public AuthenticationServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public String getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        final Object principal = authentication.getPrincipal();
        if (principal == null) {
            return null;
        }
        return principal.toString();
    }

    @Override
    public Account getCurrentUser() {
        final String username = getCurrentUsername();
        if (username == null) {
            return null;
        }
        return accountRepository.findByAccountName(username);
    }
}
