package com.greenlight.global.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RestTemplateConfigTest {

    @Autowired
    RestTemplate restTemplate;

    @Disabled
    @Test
    @DisplayName("springboot 실행 후 테스트 가능")
    void restTemplateTest() {
        // given
        MemberTestDto memberTestDto = new MemberTestDto();
        memberTestDto.setMemberId("junhyune1");
        memberTestDto.setPassword("12345678aA!");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("traceId", "testMDCunique");

        HttpEntity<MemberTestDto> memberTestDtoHttpEntity = new HttpEntity<>(memberTestDto, httpHeaders);

        // when
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:8080/api/v1/member/signIn", HttpMethod.POST, memberTestDtoHttpEntity, String.class);
        HttpHeaders headers = responseEntity.getHeaders();

        // then
        System.out.println("headers = " + headers.get("traceId"));
        System.out.println("return body = " + responseEntity.getBody());
        assertThat(headers.get("traceId")).isNotEmpty();

    }

    @Getter
    @Setter
    @ToString
    static class MemberTestDto {

        private String memberId;
        private String password;

    }
}
