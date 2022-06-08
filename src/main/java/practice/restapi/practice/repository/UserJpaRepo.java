package practice.restapi.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.restapi.practice.entity.User;

import java.util.Optional;

public interface UserJpaRepo extends JpaRepository<User, Long> {


    Optional<User> findByUid(String email);
}
