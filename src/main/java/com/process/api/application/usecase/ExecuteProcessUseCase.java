package com.process.api.application.usecase;

import com.process.api.domain.enums.ProcessStatus;
import com.process.api.domain.model.Process;
import com.process.api.infrastructure.persistence.entity.ProcessEntity;
import com.process.api.infrastructure.persistence.mapper.ProcessMapper;
import com.process.api.infrastructure.persistence.repository.ProcessRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExecuteProcessUseCase {

    private final ProcessRepository repository;

    @Async
    @Transactional
    public void execute(Long processId) {

        try {
            //  ENTITY → DOMAIN
            ProcessEntity entity = repository.findById(processId)
                    .orElseThrow(() -> new RuntimeException("Processo não encontrado"));

            Process process = ProcessMapper.toDomain(entity);

            //  PROCESSING
            process.setStatus(ProcessStatus.PROCESSING);
            process.setUpdatedAt(LocalDateTime.now());

            repository.save(ProcessMapper.toEntity(process));

            log.info("Process {} iniciado", processId);

            // Simulação
            Thread.sleep(3000);

            //  BUSCA NOVAMENTE (controle de versão)
            ProcessEntity updatedEntity = repository.findById(processId)
                    .orElseThrow(() -> new RuntimeException("Processo não encontrado"));

            Process updatedProcess = ProcessMapper.toDomain(updatedEntity);

            //  FINALIZA
            updatedProcess.setStatus(ProcessStatus.DONE);
            updatedProcess.setResult("Processado com sucesso");
            updatedProcess.setUpdatedAt(LocalDateTime.now());

            repository.save(ProcessMapper.toEntity(updatedProcess));

            log.info("Process {} finalizado com sucesso", processId);

        } catch (Exception e) {

            log.error("Erro ao processar {}: {}", processId, e.getMessage());

            try {
                ProcessEntity failedEntity = repository.findById(processId)
                        .orElseThrow(() -> new RuntimeException("Processo não encontrado"));

                Process failedProcess = ProcessMapper.toDomain(failedEntity);

                failedProcess.setStatus(ProcessStatus.FAILED);
                failedProcess.setResult(e.getMessage());
                failedProcess.setUpdatedAt(LocalDateTime.now());

                repository.save(ProcessMapper.toEntity(failedProcess));

            } catch (Exception ex) {
                log.error("Erro ao atualizar FAILED {}: {}", processId, ex.getMessage());
            }
        }
    }
}