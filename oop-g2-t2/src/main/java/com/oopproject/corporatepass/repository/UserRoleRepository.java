package com.oopproject.corporatepass.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oopproject.corporatepass.model.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {
    List<UserRole> findByEmailEquals(String email);
    void deleteByEmail(String email);
}
