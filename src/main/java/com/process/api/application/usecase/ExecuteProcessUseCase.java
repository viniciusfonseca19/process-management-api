package com.process.api.application.usecase;

import com.process.api.application.service.ProcessAsyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExecuteProcessUseCase {

    private final ProcessAsyncService asyncService;

    public void execute(Long processId) {
        asyncService.process(processId);
    }
}