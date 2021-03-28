package com.github.gatoke.offers.port.adapter.rest;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Data
@Component
@RequestScope
public class RequestPerformerContext {

    private String person;
}
