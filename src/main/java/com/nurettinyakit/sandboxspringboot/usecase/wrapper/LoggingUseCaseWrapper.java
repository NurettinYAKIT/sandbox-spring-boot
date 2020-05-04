package com.nurettinyakit.sandboxspringboot.usecase.wrapper;

import com.nurettinyakit.sandboxspringboot.usecase.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class LoggingUseCaseWrapper<REQUEST_TYPE, RESPONSE_TYPE> implements UseCase<REQUEST_TYPE, RESPONSE_TYPE> {

    private final UseCase<REQUEST_TYPE, RESPONSE_TYPE> wrappedUseCase;

    @Override
    public RESPONSE_TYPE execute(final REQUEST_TYPE request) {
        log.debug("Executing usecase for {} parameters {}", request.getClass().getSimpleName(), request);
        try {
            return wrappedUseCase.execute(request);
        } finally {
            log.debug("Finished executing usecase for {}", request.getClass().getSimpleName());
        }
    }
}
