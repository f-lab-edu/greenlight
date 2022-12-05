package com.greenlight.auth.ui;

import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenlight.auth.application.MemberService;
import com.greenlight.auth.ui.request.MemberRequest;
import com.greenlight.auth.ui.response.MemberResponse;
import com.greenlight.auth.ui.response.Response;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signUp")
    public Response<MemberResponse> signUp(
            @Valid @RequestBody MemberRequest memberRequest) {
        return Response.success(memberService.signUp(memberRequest));
    }

}
