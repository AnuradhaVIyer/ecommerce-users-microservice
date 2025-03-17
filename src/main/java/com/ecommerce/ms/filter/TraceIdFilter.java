package com.ecommerce.ms.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import jakarta.servlet.*;
import java.io.IOException;
import java.util.UUID;

@Component
public class TraceIdFilter implements Filter {
	
	private static final Logger log = LoggerFactory.getLogger(TraceIdFilter.class); // ✅ Logger Initialization
	
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
    	String traceId = UUID.randomUUID().toString();
        MDC.put("traceId", traceId);
        try {
            log.info("Generated traceId: {}", traceId); // Add this line to verify
            chain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}
