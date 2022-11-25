package com.oopproject.corporatepass.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oopproject.corporatepass.model.Attractions;

@Repository
public interface AttractionsRepository extends JpaRepository<Attractions, String> {
    Optional<Attractions> findByNameEquals(String attractionName);
    Optional<Attractions> findByAttractionIdEquals(int attractionId);
}
