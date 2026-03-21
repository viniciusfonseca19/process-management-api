package com.process.api.application.dto.request;

import com.process.api.domain.enums.ProcessType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProcessRequestDTO {

    @NotNull
    private ProcessType type;

    @NotBlank
    private String payload;
}