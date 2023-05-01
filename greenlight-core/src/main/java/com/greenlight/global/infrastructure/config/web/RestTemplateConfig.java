package com.greenlight.global.infrastructure.config.web;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.MDC;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Configuration
public class RestTemplateConfig {

    private RestTemplate restTemplate;

   가
    public void init() {
        restTemplate = new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(5))
                .additionalInterceptors(clientHttpRequestInterceptor(), new LoggingInterceptor())
                .requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
                .build();
    }

    @Bean
    public RestTemplate restTemplate() {
        return restTemplate;
    }

    public ClientHttpRequestInterceptor clientHttpRequestInterceptor() {

        return (request, body, execution) -> {
            RetryTemplate retryTemplate = new RetryTemplate();
            retryTemplate.setRetryPolicy(new SimpleRetryPolicy(3));

            return retryTemplate.execute(context -> execution.execute(request, body));
        };
    }

    private class LoggingInterceptor implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            String traceId = MDC.get("traceId");
            if (!StringUtils.hasText(traceId)) {
                traceId = UUID.randomUUID().toString();
            }

            request.getHeaders().add("traceId", traceId);
            printRequest(traceId, request, body);

            ClientHttpResponse response = execution.execute(request, body);

            response.getHeaders().add("traceId", traceId);
            printResponse(traceId, response);

            return response;
        }

        private void printRequest(final String traceId, final HttpRequest req, final byte[] body) {
            log.info("[{}] URI: {}, Method: {}, Headers:{}, Body:{} ",
                    traceId, req.getURI(), req.getMethod(), req.getHeaders(), new String(body, StandardCharsets.UTF_8));
        }

        private void printResponse(final String traceId, final ClientHttpResponse res) throws IOException {
            String body = new BufferedReader(new InputStreamReader(res.getBody(), StandardCharsets.UTF_8)).lines()
                    .collect(Collectors.joining("\n"));

            log.info("[{}] Status: {}, Headers:{}, Body:{} ",
                    traceId, res.getStatusCode(), res.getHeaders(), body);
        }
    }
}
