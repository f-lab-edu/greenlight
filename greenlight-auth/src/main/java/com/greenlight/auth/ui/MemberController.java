package com.greenlight.auth.ui;

import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenlight.auth.application.MemberService;
import com.greenlight.auth.ui.dto.request.MemberRequest;
import com.greenlight.auth.ui.dto.response.MemberResponse;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("test text");
    }

    @PostMapping("/signUp")
    public ResponseEntity<MemberResponse> signUp(
            @Valid @RequestBody MemberRequest memberRequestDto) {
        return ResponseEntity.ok(memberService.signUp(memberRequestDto));
    }

}
