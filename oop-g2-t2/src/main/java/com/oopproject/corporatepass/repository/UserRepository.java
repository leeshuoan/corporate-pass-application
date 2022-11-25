package com.oopproject.corporatepass.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.oopproject.corporatepass.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmailEquals(String email);

    @Modifying
    @Query(value = "UPDATE user set is_banned = ?2 where email = ?1", nativeQuery = true)
    void updateUserIsBanned(String email, int i);

    @Modifying
    @Query(value = "UPDATE user set username = ?2, name = ?3, contact = ?4 where email = ?1", nativeQuery = true)
    void updateUserDetails(String email, String username, String name, int contact);

    // get all admins email
    @Query(value = "SELECT u.email FROM user u join user_role ur on u.email = ur.email join role r on ur.role_id = r.id where r.name = 'admin'", nativeQuery = true)
    String[] getAllAdminsEmail();
}
