package com.example.application.services;

import com.example.application.repositories.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.application.entities.Audit;
import java.util.List;

@Service
public class AuditService {

    private final AuditRepository auditRepository;

    @Autowired
    public AuditService(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    // Read
    public List<Audit> getAllAudits() { return auditRepository.findAll(); }

    // Search
    public List<Audit> searchAuditByName(String name) {
        return auditRepository.findByFullnameContainingIgnoreCase(name);
    }
}