package com.github.gatoke.offers.port.adapter.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OperationPerformerProvider {

    private final RequestPerformerContext requestPerformerContext;

    public String getOperationPerformer() {
        try {
            return requestPerformerContext.getPerson();
        } catch (final BeanCreationException e) {
            return "SYSTEM";
        }
    }
}
