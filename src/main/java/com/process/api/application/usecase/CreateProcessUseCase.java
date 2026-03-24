package com.process.api.application.usecase;

import com.process.api.application.dto.request.ProcessRequestDTO;
import com.process.api.application.dto.response.ProcessResponseDTO;
import com.process.api.application.usecase.service.ProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateProcessUseCase {

    private final ProcessService service;

    public ProcessResponseDTO execute(ProcessRequestDTO dto) {
        return service.create(dto);
    }
}