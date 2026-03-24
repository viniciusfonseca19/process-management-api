package com.process.api.application.usecase.service;

import com.process.api.domain.enums.ProcessStatus;
import com.process.api.domain.model.Process;
import com.process.api.infrastructure.persistence.repository.ProcessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProcessAsyncService {

    private final ProcessRepository repository;

    @Async
    @Transactional
    public void process(Long id) {

        try {
            // Busca o processo (estado mais recente)
            Process process = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Processo não encontrado"));

            // Atualiza para PROCESSING
            process.setStatus(ProcessStatus.PROCESSING);
            process.setUpdatedAt(LocalDateTime.now());
            repository.save(process);

            log.info("Process {} iniciado", id);

            // Simulação de processamento (email, relatório, etc.)
            Thread.sleep(3000);

            // Busca novamente para evitar problema de versão
            Process updatedProcess = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Processo não encontrado"));

            // Finaliza como DONE
            updatedProcess.setStatus(ProcessStatus.DONE);
            updatedProcess.setResult("Processado com sucesso");
            updatedProcess.setUpdatedAt(LocalDateTime.now());

            repository.save(updatedProcess);

            log.info("Process {} finalizado com sucesso", id);

        } catch (Exception e) {

            log.error("Erro ao processar {}: {}", id, e.getMessage());

            try {
                Process failedProcess = repository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Processo não encontrado"));

                failedProcess.setStatus(ProcessStatus.FAILED);
                failedProcess.setResult(e.getMessage());
                failedProcess.setUpdatedAt(LocalDateTime.now());

                repository.save(failedProcess);

            } catch (Exception ex) {
                log.error("Erro ao atualizar status para FAILED do processo {}: {}", id, ex.getMessage());
            }
        }
    }
}