package com.greenlight.auth.ui;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.greenlight.ObjectJsonConverter;
import com.greenlight.auth.application.MemberService;
import com.greenlight.auth.configuration.jwt.JwtFilter;
import com.greenlight.auth.ui.request.MemberRequest;
import com.greenlight.auth.ui.response.MemberResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(value = MemberController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtFilter.class)})
class MemberControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    MemberService memberService;

    @Test
    @DisplayName("회원가입 성공")
    @WithMockUser
    void sign_success() throws Exception {
        MemberRequest memberRequest = MemberRequest.builder()
                .memberId("junhyun")
                .name("주년")
                .email("moya@gmail.com")
                .password("password")
                .build();

        when(memberService.signUp(any())).thenReturn(mock(MemberResponse.class));

        mockMvc.perform(post("/api/signUp")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(ObjectJsonConverter.convertToJson(memberRequest)))
            .andDo(print())
                .andExpect(status().isOk());
    }


}
