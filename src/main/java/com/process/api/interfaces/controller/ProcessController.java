package com.process.api.interfaces.controller;

import com.process.api.application.dto.request.ProcessRequestDTO;
import com.process.api.application.dto.response.ProcessResponseDTO;
import com.process.api.application.usecase.CreateProcessUseCase;
import com.process.api.application.usecase.ExecuteProcessUseCase;
import com.process.api.application.usecase.RetryProcessUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/processes")
@RequiredArgsConstructor
public class ProcessController {

    private final CreateProcessUseCase createUseCase;
    private final ExecuteProcessUseCase executeUseCase;
    private final RetryProcessUseCase retryUseCase;

    @PostMapping
    public ProcessResponseDTO create(@RequestBody @Valid ProcessRequestDTO dto) {
        ProcessResponseDTO response = createUseCase.execute(dto);

        //  dispara o processamento async
        executeUseCase.execute(response.getId());

        return response;
    }

    @PostMapping("/{id}/retry")
    public void retry(@PathVariable Long id) {
        retryUseCase.execute(id);

        //  reprocessa novamente
        executeUseCase.execute(id);
    }
}