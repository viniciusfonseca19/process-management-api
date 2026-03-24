package com.process.api.application.usecase.service;

import com.process.api.domain.enums.ProcessStatus;
import com.process.api.domain.model.Process;
import com.process.api.infrastructure.persistence.repository.ProcessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProcessExecutor {

    private final ProcessRepository repository;

    public void execute(Process process) {
        try {
            log.info("Processando ID: {}", process.getId());

            process.setStatus(ProcessStatus.PROCESSING);
            process.setUpdatedAt(LocalDateTime.now());
            repository.save(process);

            // Simulação de processamento
            Thread.sleep(3000);

            process.setStatus(ProcessStatus.DONE);
            process.setResult("Processado com sucesso!");
            process.setUpdatedAt(LocalDateTime.now());

            repository.save(process);

            log.info("Processo {} finalizado com sucesso", process.getId());

        } catch (Exception e) {
            log.error("Erro no processo {}", process.getId(), e);

            process.setStatus(ProcessStatus.FAILED);
            process.setResult("Erro: " + e.getMessage());
            process.setUpdatedAt(LocalDateTime.now());

            repository.save(process);
        }
    }
}