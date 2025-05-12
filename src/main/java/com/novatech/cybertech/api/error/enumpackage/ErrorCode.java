package com.novatech.cybertech.api.error.enumpackage;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    APPLICATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCodeType.TECHNICAL),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, ErrorCodeType.TECHNICAL),
    WITHDRAWAL_EXCEEDING_OVERDRAFT(HttpStatus.FORBIDDEN, ErrorCodeType.FUNCTIONAL),
    ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND, ErrorCodeType.FUNCTIONAL),
    DEPOSIT_LIMIT_REACHED(HttpStatus.FORBIDDEN, ErrorCodeType.FUNCTIONAL),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, ErrorCodeType.TECHNICAL),
    OPERATION_NOT_FOUND(HttpStatus.NOT_FOUND, ErrorCodeType.FUNCTIONAL);

    private final HttpStatus responseStatus;
    private final ErrorCodeType errorCodeType;
}
