package repository;

import domain.user;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface userRepository extends JpaRepository<user, Long> {
    Optional<user> findByemail(String email);
}
