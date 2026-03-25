package com.process.api.application.usecase;

import com.process.api.application.dto.request.ProcessRequestDTO;
import com.process.api.application.dto.response.ProcessResponseDTO;
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
public class CreateProcessUseCase {

    private final ProcessRepository repository;

    public ProcessResponseDTO execute(ProcessRequestDTO dto) {

        //  DTO → DOMAIN
        Process process = Process.builder()
                .type(dto.getType())
                .status(ProcessStatus.PENDING)
                .payload(dto.getPayload())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        //  DOMAIN → ENTITY
        ProcessEntity entity = ProcessMapper.toEntity(process);

        //  SALVA
        ProcessEntity saved = repository.save(entity);

        //  ENTITY → DOMAIN
        Process savedProcess = ProcessMapper.toDomain(saved);

        //  DOMAIN → RESPONSE DTO
        return ProcessResponseDTO.builder()
                .id(savedProcess.getId())
                .status(savedProcess.getStatus())
                .type(savedProcess.getType())
                .build();
    }
}