package com.picpaysimplificado.repositories;

import com.picpaysimplificado.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserRepository, Long> {

    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByDocument(String document);

}
