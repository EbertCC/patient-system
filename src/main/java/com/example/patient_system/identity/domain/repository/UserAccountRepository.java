package com.example.patient_system.identity.domain.repository;

import com.example.patient_system.identity.domain.model.UserAccount;
import com.example.patient_system.shared.domain.Email;

import java.util.Optional;

public interface UserAccountRepository {
    UserAccount save(UserAccount account);
    Optional<UserAccount> findByEmail(Email email);
}
