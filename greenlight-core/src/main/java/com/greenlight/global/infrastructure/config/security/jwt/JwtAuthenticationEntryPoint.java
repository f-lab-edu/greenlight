package com.greenlight.global.infrastructure.config.security.jwt;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.greenlight.global.domain.ObjectJsonConverter;
import com.greenlight.global.domain.model.constants.common.ResponseCommonCode;
import com.greenlight.global.presentation.response.ApiResponse;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

   /**
    *   유효한 자격증명을 제공하지 않고 접근하려 할때 401
    */
   @Override
   public void commence(HttpServletRequest request,
                        HttpServletResponse response,
                        AuthenticationException authException) throws IOException {
      String jwtExceptionCode = (String) request.getAttribute("jwtExceptionCode");
       if (jwtExceptionCode == null) {
           response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
           return;
       }
       setSendResponse(response, getResponseCommonCode(jwtExceptionCode));
   }

   private ResponseCommonCode getResponseCommonCode(String jwtExceptionCode) {
       if (jwtExceptionCode.equals(ResponseCommonCode.FAILED_INVALID_JWT_SIGNATURE.getCode())) {
           return ResponseCommonCode.FAILED_INVALID_JWT_SIGNATURE;
       } else if (jwtExceptionCode.equals(ResponseCommonCode.FAILED_EXPIRED_JWT_TOKEN.getCode())) {
           return ResponseCommonCode.FAILED_EXPIRED_JWT_TOKEN;
       } else if (jwtExceptionCode.equals(ResponseCommonCode.FAILED_UNSUPPORTED_JWT_TOKEN.getCode())) {
           return ResponseCommonCode.FAILED_UNSUPPORTED_JWT_TOKEN;
       } else if (jwtExceptionCode.equals(ResponseCommonCode.FAILED_ILLEGALARGUMENT_JWT_TOKEN.getCode())) {
           return ResponseCommonCode.FAILED_ILLEGALARGUMENT_JWT_TOKEN;
       } else {
           return ResponseCommonCode.FAILED_SYSTEM;
       }
   }

   private void setSendResponse(HttpServletResponse response, ResponseCommonCode responseCommonCode) throws IOException {
       response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
       response.setContentType(MediaType.APPLICATION_JSON_VALUE);
       response.setCharacterEncoding("UTF-8");
       response.getWriter().write(
               ObjectJsonConverter.convertToJson(ApiResponse.fail(
                       new ApiResponse.Error(
                               responseCommonCode.getCode(),
                               responseCommonCode.getMessage()
                       )
               ))
       );
   }
}
