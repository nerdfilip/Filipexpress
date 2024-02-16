package com.example.application.repositories;

import com.example.application.entities.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Integer> {
    List<Audit> findByFullnameContainingIgnoreCase(String name);
}
