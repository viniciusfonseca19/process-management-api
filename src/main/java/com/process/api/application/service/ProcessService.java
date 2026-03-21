package com.process.api.application.service;

import com.process.api.application.dto.request.ProcessRequestDTO;
import com.process.api.application.dto.response.ProcessResponseDTO;
import com.process.api.domain.enums.ProcessStatus;
import com.process.api.domain.model.Process;
import com.process.api.infrastructure.persistence.repository.ProcessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProcessService {

    private final ProcessRepository repository;

    public ProcessResponseDTO create(ProcessRequestDTO dto) {

        Process process = Process.builder()
                .type(dto.getType())
                .payload(dto.getPayload())
                .status(ProcessStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Process saved = repository.save(process);

        return toResponse(saved);
    }

    public Page<ProcessResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(this::toResponse);
    }

    public ProcessResponseDTO findById(Long id) {
        Process process = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Processo não encontrado"));

        return toResponse(process);
    }

    public void retry(Long id) {
        Process process = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Processo não encontrado"));

        if (process.getStatus() != ProcessStatus.FAILED) {
            throw new RuntimeException("Só pode retry em FAILED");
        }

        process.setStatus(ProcessStatus.PENDING);
        process.setUpdatedAt(LocalDateTime.now());

        repository.save(process);
    }

    private ProcessResponseDTO toResponse(Process process) {
        return ProcessResponseDTO.builder()
                .id(process.getId())
                .type(process.getType())
                .status(process.getStatus())
                .payload(process.getPayload())
                .result(process.getResult())
                .createdAt(process.getCreatedAt())
                .updatedAt(process.getUpdatedAt())
                .build();
    }
}