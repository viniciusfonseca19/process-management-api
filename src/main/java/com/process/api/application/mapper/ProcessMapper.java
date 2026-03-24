package com.process.api.application.mapper;

import com.process.api.domain.model.Process;
import com.process.api.infrastructure.persistence.entity.ProcessEntity;

public class ProcessMapper {

    public static ProcessEntity toEntity(Process process) {
        return ProcessEntity.builder()
                .id(process.getId())
                .type(process.getType())
                .status(process.getStatus())
                .payload(process.getPayload())
                .result(process.getResult())
                .createdAt(process.getCreatedAt())
                .updatedAt(process.getUpdatedAt())
                .version(process.getVersion())
                .build();
    }

    public static Process toDomain(ProcessEntity entity) {
        return Process.builder()
                .id(entity.getId())
                .type(entity.getType())
                .status(entity.getStatus())
                .payload(entity.getPayload())
                .result(entity.getResult())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .version(entity.getVersion())
                .build();
    }
}