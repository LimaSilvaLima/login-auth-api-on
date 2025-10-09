package com.login_auth_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.login_auth_api.domain.User;

public interface UserRepository  extends JpaRepository<User, String> {
    User findByEmail(String email);
    User findByEmailAndPassword(String email, String password);

}
