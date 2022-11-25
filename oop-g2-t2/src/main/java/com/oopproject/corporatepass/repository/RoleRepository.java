package com.oopproject.corporatepass.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oopproject.corporatepass.model.Role;
@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByIdEquals(int id);
    List<Role> findIdByName(String roleName);
}
