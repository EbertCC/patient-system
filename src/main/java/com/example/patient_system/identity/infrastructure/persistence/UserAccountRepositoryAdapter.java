package com.example.patient_system.identity.infrastructure.persistence;

import com.example.patient_system.identity.domain.model.HashedPassword;
import com.example.patient_system.identity.domain.model.UserAccount;
import com.example.patient_system.identity.domain.repository.UserAccountRepository;
import com.example.patient_system.shared.domain.Email;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserAccountRepositoryAdapter implements UserAccountRepository {

    private final SpringDataUserAccountRepository jpa;

    public UserAccountRepositoryAdapter(SpringDataUserAccountRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public UserAccount save(UserAccount account) {
        UserAccountEntity e = new UserAccountEntity();
        if (account.id() != null) e.setId(account.id());
        e.setEmail(account.email().value());
        e.setPassword(account.password().value());
        UserAccountEntity saved = jpa.save(e);
        return toDomain(saved);
    }

    @Override
    public Optional<UserAccount> findByEmail(Email email) {
        return jpa.findByEmail(email.value()).map(this::toDomain);
    }

    private UserAccount toDomain(UserAccountEntity e) {
        UserAccount a = new UserAccount(e.getId(), Email.of(e.getEmail()), HashedPassword.of(e.getPassword()));
        return a;
    }
}
