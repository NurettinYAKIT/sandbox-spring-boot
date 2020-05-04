package com.nurettinyakit.sandboxspringboot.configuration;

import com.nurettinyakit.sandboxspringboot.usecase.UseCase;
import com.nurettinyakit.sandboxspringboot.usecase.wrapper.LoggingUseCaseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UseCaseWrappingBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(@Nullable final Object bean, final String beanName) throws BeansException {
        if (bean instanceof UseCase<?, ?>) {
            log.debug("Wrapping {}.", beanName);
            return new LoggingUseCaseWrapper((UseCase<?, ?>) bean);
        }
        return bean;
    }
}
