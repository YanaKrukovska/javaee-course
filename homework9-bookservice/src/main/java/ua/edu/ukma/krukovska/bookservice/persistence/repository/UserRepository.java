package ua.edu.ukma.krukovska.bookservice.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ukma.krukovska.bookservice.persistence.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByEmailIgnoreCase(String email);
    User findUserByUsernameIgnoreCase(String username);

}