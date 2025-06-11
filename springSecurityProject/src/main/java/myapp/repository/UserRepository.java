package myapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import myapp.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserLoginId(String userLoginId);
}