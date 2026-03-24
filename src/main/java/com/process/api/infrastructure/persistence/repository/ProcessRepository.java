package com.process.api.infrastructure.persistence.repository;

import com.process.api.domain.model.Process;
import com.process.api.infrastructure.persistence.entity.ProcessEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessRepository extends JpaRepository<ProcessEntity, Long> {
}