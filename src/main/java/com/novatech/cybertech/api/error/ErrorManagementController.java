package com.novatech.cybertech.api.error;



import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.novatech.cybertech.api.error.model.ErrorResponseDto;
import com.novatech.cybertech.exceptions.AccountNotFoundException;
import com.novatech.cybertech.exceptions.DepositLimitExceededException;
import com.novatech.cybertech.exceptions.OperationNotFoundException;
import com.novatech.cybertech.exceptions.WithdrawalAmountBiggerThanBalanceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import static com.novatech.cybertech.api.error.enumpackage.ErrorCode.*;


@ControllerAdvice
public class ErrorManagementController {

    @ExceptionHandler(WithdrawalAmountBiggerThanBalanceException.class)
    public ResponseEntity<ErrorResponseDto> handleWithdrawalAmountBiggerThanBalanceException(WithdrawalAmountBiggerThanBalanceException exception) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(exception.getMessage(), WITHDRAWAL_EXCEEDING_OVERDRAFT.getResponseStatus().value(), WITHDRAWAL_EXCEEDING_OVERDRAFT.getErrorCodeType());
        return new ResponseEntity<>(errorResponseDto, WITHDRAWAL_EXCEEDING_OVERDRAFT.getResponseStatus());
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleAccountNotFoundException(AccountNotFoundException exception) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(exception.getMessage(), ACCOUNT_NOT_FOUND.getResponseStatus().value(), ACCOUNT_NOT_FOUND.getErrorCodeType());
        return new ResponseEntity<>(errorResponseDto, ACCOUNT_NOT_FOUND.getResponseStatus());
    }

    @ExceptionHandler(OperationNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleOperationNotFoundException(OperationNotFoundException exception) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(exception.getMessage(), OPERATION_NOT_FOUND.getResponseStatus().value(), ACCOUNT_NOT_FOUND.getErrorCodeType());
        return new ResponseEntity<>(errorResponseDto, OPERATION_NOT_FOUND.getResponseStatus());
    }

    @ExceptionHandler(DepositLimitExceededException.class)
    public ResponseEntity<ErrorResponseDto> handleDepositLimitExceededException(DepositLimitExceededException exception) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(exception.getMessage(), DEPOSIT_LIMIT_REACHED.getResponseStatus().value(), DEPOSIT_LIMIT_REACHED.getErrorCodeType());
        return new ResponseEntity<>(errorResponseDto, DEPOSIT_LIMIT_REACHED.getResponseStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto("Invalid Request or Request Poorly Constructed", INVALID_REQUEST.getResponseStatus().value(), INVALID_REQUEST.getErrorCodeType());
        return new ResponseEntity<>(errorResponseDto, INVALID_REQUEST.getResponseStatus());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNoResourceFoundException(NoResourceFoundException exception) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto("The page you're asking for doesn't exists :(", RESOURCE_NOT_FOUND.getResponseStatus().value(), RESOURCE_NOT_FOUND.getErrorCodeType());
        return new ResponseEntity<>(errorResponseDto, RESOURCE_NOT_FOUND.getResponseStatus());
    }

    @ExceptionHandler(UnrecognizedPropertyException.class)
    public ResponseEntity<String> handleUnrecognizedPropertyException(UnrecognizedPropertyException ex) {
        return new ResponseEntity<>("Invalid request: Unknown property '" + ex.getPropertyName() + "'", HttpStatus.BAD_REQUEST);
    }
}