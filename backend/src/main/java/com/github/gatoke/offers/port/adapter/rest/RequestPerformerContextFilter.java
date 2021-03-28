package com.github.gatoke.offers.port.adapter.rest;

import com.github.gatoke.offers.port.adapter.configuration.ApiKeyConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RequestPerformerContextFilter extends OncePerRequestFilter {

    private final RequestPerformerContext requestPerformerContext;
    private final ApiKeyConfiguration apiKeyConfiguration;

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws IOException, ServletException {
        final String apiKey = request.getHeader("x-api-key");
        final Optional<String> clientName = apiKeyConfiguration.findClientName(apiKey);
        clientName.ifPresent(requestPerformerContext::setPerson);
        filterChain.doFilter(request, response);
    }
}
