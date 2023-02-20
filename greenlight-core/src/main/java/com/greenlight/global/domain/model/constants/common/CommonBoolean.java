package com.greenlight.global.domain.model.constants.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommonBoolean {

    Y(1, true),
    N(0, false);

    private final int intYN;
    private final boolean bYN;
}
