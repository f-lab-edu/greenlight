package com.greenlight.global.application.processor;

import com.greenlight.global.domain.auth.Auth;
import lombok.Getter;

public class MemberSignInProcessor {

    private final Auth auth;

    public MemberSignInProcessor(Auth auth) {
        this.auth = auth;
    }

    public Result execute(Command command) {
        return new Result(auth.authorize(command));
    }

    @Getter
    public static class Command {
        private String memberId;
        private String password;

        public Command(String memberId, String password) {
            this.memberId = memberId;
            this.password = password;
        }
    }

    @Getter
    public static class Result {
        private String token;

        public Result(String token) {
            this.token = token;
        }
    }
}
