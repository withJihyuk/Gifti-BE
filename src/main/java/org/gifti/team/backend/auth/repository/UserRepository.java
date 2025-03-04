package org.gifti.team.backend.auth.repository;

import org.gifti.team.backend.auth.entity.User;
import org.springframework.data.
        jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findAllByEmail(String email);
}
