package com.greenlight.global.infrastructure.config.security.jwt;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.greenlight.global.domain.ObjectJsonConverter;
import com.greenlight.global.domain.model.constants.common.ResponseCommonCode;
import com.greenlight.global.presentation.response.ApiResponse;

@Slf4j
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

   /**
    * 필요한 권한이 없이 접근하려 할때 403
    */
   @Override
   public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.setCharacterEncoding("UTF-8");
      response.getWriter().write(
              ObjectJsonConverter.convertToJson(ApiResponse.fail(
                      new ApiResponse.Error(
                              ResponseCommonCode.FAILED_ROLE_PERMISSIONS.getCode(),
                              ResponseCommonCode.FAILED_ROLE_PERMISSIONS.getMessage()
                      )
              ))
      );
   }
}
