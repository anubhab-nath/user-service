package dev.anubhav.userservice.repos;

import dev.anubhav.userservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query(value = "select * from users u where u.email = ?1", nativeQuery = true)
    Optional<User> findByEmailId(String emailId);
}
