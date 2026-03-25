package com.process.api.application.usecase;

import com.process.api.domain.enums.ProcessStatus;
import com.process.api.domain.model.Process;
import com.process.api.infrastructure.persistence.entity.ProcessEntity;
import com.process.api.infrastructure.persistence.mapper.ProcessMapper;
import com.process.api.infrastructure.persistence.repository.ProcessRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class RetryProcessUseCase {

    private final ProcessRepository repository;

    public void execute(Long id) {

        //  Busca entity
        ProcessEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Processo não encontrado"));

        //  ENTITY → DOMAIN
        Process process = ProcessMapper.toDomain(entity);

        //  Validação (opcional mas MUITO importante)
        if (process.getStatus() != ProcessStatus.FAILED) {
            throw new RuntimeException("Só é possível dar retry em processos com status FAILED");
        }

        //  RESET
        process.setStatus(ProcessStatus.PENDING);
        process.setResult(null);
        process.setUpdatedAt(LocalDateTime.now());

        // 🔹 Salva
        repository.save(ProcessMapper.toEntity(process));
    }
}