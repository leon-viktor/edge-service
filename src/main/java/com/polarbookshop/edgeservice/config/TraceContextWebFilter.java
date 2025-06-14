package com.polarbookshop.edgeservice.config;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class TraceContextWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return Mono.deferContextual(ctx -> {
            String traceId = ctx.getOrDefault("traceId", "");
            String spanId = ctx.getOrDefault("spanId", "");

            MDC.put("trace_id", traceId);
            MDC.put("span_id", spanId);

            return chain.filter(exchange)
                    .doFinally(signal -> {
                        MDC.remove("trace_id");
                        MDC.remove("span_id");
                    });
        });
    }
}