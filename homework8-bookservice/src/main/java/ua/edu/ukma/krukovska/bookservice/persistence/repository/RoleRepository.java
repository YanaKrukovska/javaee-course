package ua.edu.ukma.krukovska.bookservice.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ukma.krukovska.bookservice.persistence.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}