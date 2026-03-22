package com.process.api.application.service;

import com.process.api.domain.enums.ProcessStatus;
import com.process.api.domain.model.Process;
import com.process.api.infrastructure.persistence.repository.ProcessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProcessAsyncService {

    private final ProcessRepository repository;

    @Async
    public void process(Long processId) {

        Process process = repository.findById(processId)
                .orElseThrow(() -> new RuntimeException("Processo não encontrado"));

        try {
            // Atualiza para PROCESSING
            process.setStatus(ProcessStatus.PROCESSING);
            process.setUpdatedAt(LocalDateTime.now());
            repository.save(process);

            // Simula processamento (ex: envio de email, geração de relatório)
            Thread.sleep(3000);

            // Resultado fictício
            process.setStatus(ProcessStatus.DONE);
            process.setResult("Processamento concluído com sucesso");
            process.setUpdatedAt(LocalDateTime.now());

        } catch (Exception e) {

            process.setStatus(ProcessStatus.FAILED);
            process.setResult("Erro: " + e.getMessage());
            process.setUpdatedAt(LocalDateTime.now());

            log.error("Erro ao processar ID {}: {}", processId, e.getMessage());
        }

        repository.save(process);
    }
}