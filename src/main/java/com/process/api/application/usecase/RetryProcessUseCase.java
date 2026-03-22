package com.process.api.application.usecase;

import com.process.api.application.service.ProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RetryProcessUseCase {

    private final ProcessService service;

    public void execute(Long id) {
        service.retry(id);
    }
}