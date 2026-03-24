package com.process.api.domain.model;

import com.process.api.domain.enums.ProcessStatus;
import com.process.api.domain.enums.ProcessType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Process {

    private Long id;
    private ProcessType type;
    private ProcessStatus status;
    private String payload;
    private String result;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}