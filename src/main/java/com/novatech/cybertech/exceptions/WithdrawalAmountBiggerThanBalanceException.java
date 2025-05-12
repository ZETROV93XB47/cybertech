package com.novatech.cybertech.exceptions;

public class WithdrawalAmountBiggerThanBalanceException extends RuntimeException{
    public WithdrawalAmountBiggerThanBalanceException(String message) {
        super(message);
    }
}
