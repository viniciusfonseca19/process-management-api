package com.process.api.application.dto.response;

import com.process.api.domain.enums.ProcessStatus;
import com.process.api.domain.enums.ProcessType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProcessResponseDTO {

    private Long id;
    private ProcessType type;
    private ProcessStatus status;
    private String payload;
    private String result;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}