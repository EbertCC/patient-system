package com.example.patient_system.identity.infrastructure.security;

import com.example.patient_system.identity.domain.model.UserAccount;
import com.example.patient_system.identity.domain.repository.UserAccountRepository;
import com.example.patient_system.shared.domain.Email;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/** Puente entre Spring Security y el agregado UserAccount (autentica por email). */
@Service
public class AccountUserDetailsService implements UserDetailsService {

    private final UserAccountRepository accounts;

    public AccountUserDetailsService(UserAccountRepository accounts) {
        this.accounts = accounts;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount account = accounts.findByEmail(Email.of(username))
                .orElseThrow(() -> new UsernameNotFoundException("Cuenta no encontrada: " + username));
        return new User(account.email().value(), account.password().value(), Collections.emptyList());
    }
}
