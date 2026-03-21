package com.process.api.interfaces.controller;

import com.process.api.application.dto.request.ProcessRequestDTO;
import com.process.api.application.dto.response.ProcessResponseDTO;
import com.process.api.application.service.ProcessService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/processes")
@RequiredArgsConstructor
public class ProcessController {

    private final ProcessService service;

    @PostMapping
    public ProcessResponseDTO create(@RequestBody @Valid ProcessRequestDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public Page<ProcessResponseDTO> list(Pageable pageable) {
        return service.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ProcessResponseDTO getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping("/{id}/retry")
    public void retry(@PathVariable Long id) {
        service.retry(id);
    }
}