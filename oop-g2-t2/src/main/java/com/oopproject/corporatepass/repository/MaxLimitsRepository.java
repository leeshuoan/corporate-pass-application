package com.oopproject.corporatepass.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.oopproject.corporatepass.model.MaxLimits;

@Repository
public interface MaxLimitsRepository extends JpaRepository<MaxLimits, String> {
    @Modifying
    @Query(value = "UPDATE max_limits set bookings = ?1", nativeQuery = true)
    void updateMaxBookings(int bookings);

    @Modifying
    @Query(value = "UPDATE max_limits set loans = ?1", nativeQuery = true)
    void updateMaxLoans(int loans);

    @Query(value = "SELECT loans from max_limits", nativeQuery = true)
    int getMaxLoans();

    @Query(value = "SELECT bookings from max_limits", nativeQuery = true)
    int getMaxBookings();
}
